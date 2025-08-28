package com.lark.service.impl;

import com.google.gson.Gson;
import com.lark.api.drive.DriveApi;
import com.lark.data.bean.Response;
import com.lark.data.constants.TenantType;
import com.lark.data.dto.drive.FinishUploadResponseDTO;
import com.lark.data.dto.drive.FolderMetaInfoDTO;
import com.lark.data.dto.drive.PreUploadResponseDTO;
import com.lark.data.dto.drive.SecureLevel;
import com.lark.data.dto.drive.SecureLevelDto;
import com.lark.data.pojo.DocInfo;
import com.lark.exception.CustomException;
import com.lark.service.CommonService;
import com.lark.service.DriveService;
import com.lark.service.UserInfoService;
import com.lark.utils.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DriveServiceImpl implements DriveService {
    private static final Logger log = LoggerFactory.getLogger(DriveServiceImpl.class);
    private static final Logger logger = LoggerFactory.getLogger(DriveServiceImpl.class);
    private final int downloadSize = 8388608;
    private final int uploadSize = 4194304;
    @Value("${lark.download.path}")
    private String downloadPath;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private DriveApi driveApi;
    @Autowired
    private CommonService commonService;

    public DriveServiceImpl() {
    }

    public String getRootFolderToken(TenantType tenantType, String openId) {
        String userAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(tenantType, openId);
        Response<FolderMetaInfoDTO> response = this.driveApi.queryRootFolderMetaInfo(userAccessToken);
        if (response != null && response.getData() != null) {
            FolderMetaInfoDTO folderMetaInfo = (FolderMetaInfoDTO)response.getData();
            return folderMetaInfo.getToken();
        } else {
            throw new CustomException("用户根目录元信息获取失败");
        }
    }

    public String downloadFile(String oldUserOpenId, DocInfo docInfo) throws IOException {
        String filename = docInfo.getOldName();
        String fileToken = docInfo.getOldToken();
        String dirPath = this.downloadPath + oldUserOpenId;
        FileUtil.mkdirsIfNotExists(dirPath);
        String filepath = dirPath + File.separator + fileToken + "-" + filename;
        FileOutputStream outputStream = new FileOutputStream(filepath);
        long start = 0L;
        long end = 8388607L;
        int count = 0;
        ResponseBody body = null;

        while(true) {
            logger.info("分片下载文件:" + filename + "分片数: " + count);
            String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
            AtomicReference<ResponseBody> atomicBody = new AtomicReference();
            long finalStart = start;
            long finalEnd = end;
            this.commonService.retry(() -> atomicBody.set(this.driveApi.download(oldUserAccessToken, fileToken, finalStart, finalEnd)), 5, "分片下载");
            body = (ResponseBody)atomicBody.get();
            byte[] data = body.bytes();
            long bodySize = (long)data.length;
            outputStream.write(data);
            outputStream.flush();
            if (bodySize < 8388608L) {
                outputStream.close();
                return filepath;
            }

            start = end + 1L;
            end += 8388608L;
            ++count;
        }
    }

    public String uploadFile(String newUserOpenId, String filepath, String parentFileToken, DocInfo docInfo) throws CustomException, IOException, InterruptedException {
        String filename = docInfo.getOldName();
        AtomicReference<Response<FinishUploadResponseDTO>> atomicFinishUploadResponse = new AtomicReference();

        try {
            File file = new File(filepath);
            long size = file.length();
            if (size <= 4194304L) {
                byte[] data = new byte[(int)size];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(data);
                fileInputStream.close();
                logger.info("文件上传:" + docInfo.getOldName());
                this.commonService.retry(() -> {
                    String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
                    Response<FinishUploadResponseDTO> result = this.driveApi.upload(newUserAccessToken, filename, parentFileToken, size, FileUtil.checksum(data), data);
                    atomicFinishUploadResponse.set(result);
                }, 5, "文件上传");
                fileInputStream.close();
            } else {
                logger.info("文件预上传：" + filename);
                AtomicReference<Response<PreUploadResponseDTO>> atomicPreUploadResponse = new AtomicReference();
                this.commonService.retry(() -> {
                    Response<PreUploadResponseDTO> result = this.driveApi.preUpload(this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId), docInfo.getOldName(), parentFileToken, size);
                    atomicPreUploadResponse.set(result);
                }, 5, "文件分片预上传");
                Response<PreUploadResponseDTO> preUploadResponse = (Response)atomicPreUploadResponse.get();
                String uploadId = ((PreUploadResponseDTO)preUploadResponse.getData()).getUploadId();
                long begin = 0L;
                int uploadCount = (int)((size + 4194304L - 1L) / 4194304L);

                for(int blockNum = 0; blockNum < uploadCount; ++blockNum) {
                    int currentSize = (int)Math.min(4194304L, size - begin);
                    byte[] data = this.read(filepath, begin, currentSize);
                    logger.info("文件分片上传：" + filename + " 分片数" + blockNum);
                    int finalBlockNum = blockNum;
                    this.commonService.retry(() -> {
                        String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
                        this.driveApi.partUpload(newUserAccessToken, uploadId, finalBlockNum, data.length, FileUtil.checksum(data), data);
                    }, 5, "文件分片上传");
                    begin += 4194304L;
                }

                this.commonService.retry(() -> {
                    String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
                    Response<FinishUploadResponseDTO> result = this.driveApi.finishUpload(newUserAccessToken, uploadId, uploadCount);
                    atomicFinishUploadResponse.set(result);
                }, 5, "文件分片结束上传");
            }

            FinishUploadResponseDTO finishUploadResponse = null;
            if (atomicFinishUploadResponse.get() != null && (finishUploadResponse = (FinishUploadResponseDTO)((Response)atomicFinishUploadResponse.get()).getData()) != null) {
                return finishUploadResponse.getFileToken();
            } else {
                throw new CustomException("文件上传失败");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public SecureLevel getLowestLevel(String userToken, String lowestLevelName) {
        log.info("传入的最低密级标签名字为：{}", lowestLevelName);
        SecureLevel lowestLevel = null;
        if (!"NA".equalsIgnoreCase(lowestLevelName)) {
            try {
                Response<SecureLevelDto> response = this.driveApi.getSecureLevels(userToken);
                SecureLevelDto secureLevelDto = (SecureLevelDto)response.getData();
                List<SecureLevel> items = secureLevelDto.getItems();
                lowestLevel = (SecureLevel)items.stream().filter((level) -> level.getName().equals(lowestLevelName)).findFirst().orElse(null);
                log.info("{}, {}", (new Gson()).toJson(lowestLevel), (new Gson()).toJson(secureLevelDto));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取时间异常：{}", e.getMessage());
            }
        }

        return lowestLevel;
    }

    public List<SecureLevel> getSecureLevelList(String userToken) {
        List<SecureLevel> secureLevelList = null;

        try {
            Response<SecureLevelDto> response = this.driveApi.getSecureLevels(userToken);
            SecureLevelDto secureLevelDto = (SecureLevelDto)response.getData();
            secureLevelList = secureLevelDto.getItems();
            if (secureLevelList.isEmpty()) {
                secureLevelList = null;
            }

            log.info("{}", (new Gson()).toJson(secureLevelDto));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取时间异常：{}", e.getMessage());
        }

        return secureLevelList;
    }

    private byte[] read(String path, long position, int blockSize) throws IOException, InterruptedException {
        ByteBuffer block = ByteBuffer.allocate(blockSize);

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(path), StandardOpenOption.READ)) {
            Future<Integer> read = channel.read(block, position);

            while(!read.isDone()) {
                Thread.sleep(1L);
            }
        }

        return block.array();
    }
}
