package com.lark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.lark.api.doc.DocApi;
import com.lark.api.drive.DriveApi;
import com.lark.data.bean.Response;
import com.lark.data.constants.DocStatusEnZh;
import com.lark.data.constants.TenantType;
import com.lark.data.constants.UserIdTypeEnum;
import com.lark.data.dto.contact.UserDTO;
import com.lark.data.dto.doc.DocDTO;
import com.lark.data.dto.doc.TransferResultDTO;
import com.lark.data.dto.drive.CopyDocResponseDTO;
import com.lark.data.dto.drive.CreateFolderResponseDTO;
import com.lark.data.dto.drive.DriveMemberDTO;
import com.lark.data.dto.drive.FolderChildDTO;
import com.lark.data.dto.drive.FolderChildrenDTO;
import com.lark.data.dto.drive.QueryDocMemberResponseDTO;
import com.lark.data.dto.drive.SecureLevel;
import com.lark.data.pojo.DocInfo;
import com.lark.data.pojo.UserInfo;
import com.lark.exception.CustomException;
import com.lark.oapi.Client;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.service.drive.v1.model.PatchPermissionPublicReq;
import com.lark.oapi.service.drive.v1.model.PermissionPublicRequest;
import com.lark.service.AccessTokenService;
import com.lark.service.CommonService;
import com.lark.service.DocInfoService;
import com.lark.service.DriveService;
import com.lark.service.MessageService;
import com.lark.service.UserInfoService;
import com.lark.service.UserService;
import com.lark.service.repository.DocInfoRepository;
import com.lark.utils.FileUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DocInfoServiceImpl implements DocInfoService {
    private static final Logger log = LoggerFactory.getLogger(DocInfoServiceImpl.class);
    private static final Logger logger = LoggerFactory.getLogger(DocInfoServiceImpl.class);
    @Autowired
    private DocInfoRepository docInfoRepository;
    @Autowired
    private DocApi docApi;
    @Autowired
    private DriveApi driveApi;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private DriveService driveService;
    @Autowired
    private CommonService commonService;
    @Value("${lark.old.lowestLevelName}")
    private String oldLowestLevelName;
    @Value("${lark.new.appId}")
    private String newTenantAppId;
    @Value("${lark.new.appSecret}")
    private String newTenantAppSecret;
    @Value("${lark.old.appId}")
    private String oldTenantAppId;
    @Value("${lark.old.appSecret}")
    private String oldTenantAppSecret;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    public DocInfoServiceImpl() {
    }

    public DocDTO createDoc(String oldUserOpenId) {
        String userAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        String content = "{\"title\":{\"elements\":[{\"type\":\"textRun\",\"textRun\":{\"text\":\"请将此文档授权至接受租户下的新账号\",\"style\":{},\"location\":{\"zoneId\":\"0\",\"startIndex\":0,\"endIndex\":17}}}],\"location\":{\"zoneId\":\"0\",\"startIndex\":0,\"endIndex\":17},\"lineId\":\"0eay75\"},\"body\":{\"blocks\":[{\"type\":\"paragraph\",\"paragraph\":{\"elements\":[{\"type\":\"textRun\",\"textRun\":{\"text\":\"请将此文档授权至接受租户下的新账号！\",\"style\":{\"backColor\":{\"red\":255,\"green\":246,\"blue\":122,\"alpha\":0.8}},\"location\":{\"zoneId\":\"0\",\"startIndex\":19,\"endIndex\":37}}}],\"style\":{\"headingLevel\":1},\"location\":{\"zoneId\":\"0\",\"startIndex\":18,\"endIndex\":37},\"lineId\":\"HvBfiW\"}},{\"type\":\"paragraph\",\"paragraph\":{\"elements\":[],\"location\":{\"zoneId\":\"0\",\"startIndex\":38,\"endIndex\":38},\"lineId\":\"KI2jcB\"}}]}}";
        Response<DocDTO> response = this.docApi.createDoc(userAccessToken, (String)null, content);
        DocDTO doc = null;
        if (response != null && (doc = (DocDTO)response.getData()) != null) {
            return doc;
        } else {
            throw new CustomException("文档创建失败");
        }
    }

    @Async
    public void startTransferDocs(String oldUserOpenId, String newUserOpenId) {
        String message = "开始迁移文件";
        this.messageService.sendMessage(TenantType.OLD, message, oldUserOpenId);
        this.messageService.sendMessage(TenantType.NEW, message, newUserOpenId);
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String procStatus = "复制成功";
        String procMsg = "复制完成，无错误";

        try {
            this.getDocInfoFromLark(oldUserOpenId, newUserOpenId);
            this.copyDocInfoToNewUser(oldUserOpenId, newUserOpenId);
        } catch (Exception exception) {
            procStatus = "复制失败";
            procMsg = "可重启";
            exception.printStackTrace();
        }

        if (StringUtils.equals("复制成功", procStatus) && this.hasFailedTransferTask(oldUserOpenId, newUserOpenId)) {
            procStatus = "备份失败";
            procMsg = "可重启";
        }

        userInfo.setProcMsg(procMsg);
        userInfo.setProcStatus(procStatus);
        this.userInfoService.updateUserInfo(userInfo);
        this.messageService.sendResultNotification(oldUserOpenId, newUserOpenId);
    }

    @Async
    public void restartTransferDocs(String oldUserOpenId, String newUserOpenId) {
        String message = "重启任务";
        this.messageService.sendMessage(TenantType.OLD, message, oldUserOpenId);
        this.messageService.sendMessage(TenantType.NEW, message, newUserOpenId);
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String procStatus = "复制成功";
        String procMsg = "复制完成，无错误";

        try {
            this.copyDocInfoToNewUser(oldUserOpenId, newUserOpenId);
        } catch (Exception exception) {
            procStatus = "复制失败";
            procMsg = "可重启";
            exception.printStackTrace();
        }

        if (StringUtils.equals("复制成功", procStatus) && this.hasFailedTransferTask(oldUserOpenId, newUserOpenId)) {
            procStatus = "备份失败";
            procMsg = "可重启";
        }

        userInfo.setProcMsg(procMsg);
        userInfo.setProcStatus(procStatus);
        this.userInfoService.updateUserInfo(userInfo);
        this.messageService.sendResultNotification(oldUserOpenId, newUserOpenId);
    }

    private boolean hasFailedTransferTask(String oldUserOpenId, String newUserOpenId) {
        QueryWrapper<DocInfo> queryWrapper = new QueryWrapper();
        ((QueryWrapper)((QueryWrapper)queryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).isNull("new_token");
        int count = this.docInfoRepository.selectCount(queryWrapper);
        return count > 0;
    }

    public void getDocInfoFromLark(String oldUserOpenId, String newUserOpenId) {
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String rootFolderToken = userInfo.getOldRootFolderToken();
        int level = 0;
        userInfo.setProcStatus("开始备份");
        userInfo.setProcMsg("开始备份数据到数据库");
        this.userInfoService.updateUserInfo(userInfo);
        LinkedHashMap<String, LinkedList<FolderChildDTO>> fileCollections = new LinkedHashMap();
        fileCollections.put(rootFolderToken, this.getFolderChildrenFromLark(oldUserOpenId, rootFolderToken));
        LinkedList<String> parentDirTokens = new LinkedList();
        parentDirTokens.add(rootFolderToken);

        while(!fileCollections.isEmpty()) {
            int size = fileCollections.size();
            int count = 0;
            String parentToken = (String)parentDirTokens.removeFirst();
            LinkedList<FolderChildDTO> folderChildren = (LinkedList)fileCollections.remove(parentToken);

            while(!folderChildren.isEmpty()) {
                FolderChildDTO folderChild = (FolderChildDTO)folderChildren.removeFirst();
                DocInfo docInfo = DocInfo.builder().oldUserOpenId(oldUserOpenId).oldToken(folderChild.getToken()).oldName(folderChild.getName()).oldType(folderChild.getType()).oldLevel(level).oldParentToken(parentToken).newUserOpenId(newUserOpenId).procMsg("获取数据正常").procStatus("获取到云文档").build();
                if ("folder".equals(folderChild.getType())) {
                    docInfo.setProcStatus("获取到文件夹");
                    docInfo.setProcMsg("获取文件正常");

                    try {
                        String oldFolderToken = docInfo.getOldToken();
                        LinkedList<FolderChildDTO> temp = this.getFolderChildrenFromLark(oldUserOpenId, oldFolderToken);
                        fileCollections.put(oldFolderToken, temp);
                        parentDirTokens.add(oldFolderToken);
                    } catch (Exception var16) {
                        docInfo.setProcMsg("该文件夹下文件有获取失败情况");
                    }
                } else if ("file".equals(folderChild.getType())) {
                    docInfo.setProcMsg("文件已经获取到，但未下载");
                    docInfo.setProcStatus("未下载");
                }

                QueryWrapper<DocInfo> queryWrapper = new QueryWrapper();
                ((QueryWrapper)((QueryWrapper)queryWrapper.eq("old_token", folderChild.getToken())).eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
                DocInfo docInfoData = (DocInfo)this.docInfoRepository.selectOne(queryWrapper);
                if (docInfoData == null) {
                    this.docInfoRepository.insert(docInfo);
                } else if (Integer.valueOf(level).equals(docInfo.getOldLevel()) || StringUtils.equals(docInfo.getOldParentToken(), docInfoData.getOldParentToken())) {
                    docInfoData.setOldLevel(level);
                    docInfoData.setOldParentToken(docInfo.getOldParentToken());
                    this.docInfoRepository.updateById(docInfoData);
                }
            }

            ++count;
            if (count == size) {
                ++level;
            }
        }

    }

    public void copyDocInfoToNewUser(String oldUserOpenId, String newUserOpenId) {
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String oldRootFolderToken = userInfo.getOldRootFolderToken();
        String newRootFolderToken = userInfo.getNewRootFolderToken();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            QueryWrapper<DocInfo> folderQueryWrapper = new QueryWrapper();
            ((QueryWrapper)((QueryWrapper)folderQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("old_type", "folder");
            Map<String, DocInfo> folderMap = new HashMap();
            List<DocInfo> folderList = this.docInfoRepository.selectList(folderQueryWrapper);
            if (!CollectionUtils.isEmpty(folderList)) {
                for(DocInfo item : folderList) {
                    if (item.getNewToken() == null) {
                        this.createFolder(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, item);
                        folderMap.put(item.getOldToken(), item);
                    }
                }
            }

            List<Future<String>> futures = new ArrayList();
            QueryWrapper<DocInfo> docQueryWrapper = new QueryWrapper();
            ((QueryWrapper)((QueryWrapper)((QueryWrapper)docQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).ne("old_type", "folder")).ne("old_type", "file");
            List<DocInfo> docInfoList = new ArrayList(this.docInfoRepository.selectList(docQueryWrapper));
            if (!CollectionUtils.isEmpty(docInfoList)) {
                for(DocInfo item : docInfoList) {
                    if (item.getNewToken() == null) {
                        this.transferDoc(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, item);
                    }
                }
            }

            QueryWrapper<DocInfo> fileQueryWrapper = new QueryWrapper();
            ((QueryWrapper)((QueryWrapper)fileQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("old_type", "file");
            List<DocInfo> var27 = this.docInfoRepository.selectList(fileQueryWrapper);
            if (!CollectionUtils.isEmpty(var27)) {
                for(DocInfo docInfo : var27) {
                    if (docInfo.getNewToken() == null) {
                        Callable<String> task = () -> this.transferFile(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, docInfo);
                        Future<String> future = executorService.submit(task);
                        futures.add(future);
                    }
                }
            }

            for(Future<String> future : futures) {
                try {
                    logger.info((String)future.get());
                } catch (InterruptedException | ExecutionException e) {
                    ((Exception)e).printStackTrace();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

    public void transferDocsInfoToNewUser(String oldUserOpenId, String newUserOpenId, boolean transFailureDocs) {
        log.info("开始执行迁移。");
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String oldRootFolderToken = userInfo.getOldRootFolderToken();
        String newRootFolderToken = userInfo.getNewRootFolderToken();
        Map<String, DocInfo> folderMap = new ConcurrentHashMap();
        QueryWrapper<DocInfo> folderQueryWrapper = new QueryWrapper();
        if (transFailureDocs) {
            ((QueryWrapper)((QueryWrapper)((QueryWrapper)((QueryWrapper)folderQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("status", DocStatusEnZh.FAILURE.getEnglish())).eq("old_type", "folder")).orderByAsc("old_level");
        } else {
            ((QueryWrapper)((QueryWrapper)((QueryWrapper)folderQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("old_type", "folder")).orderByAsc("old_level");
        }

        List<DocInfo> folderList = this.docInfoRepository.selectList(folderQueryWrapper);
        if (!CollectionUtils.isEmpty(folderList)) {
            log.info("迁移第一步：创建文件夹。共需要创建 {} 个文件夹。", folderList.size());

            for(DocInfo item : folderList) {
                if (item.getNewToken() == null) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    this.createFolder(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, item);
                    folderMap.put(item.getOldToken(), item);
                }
            }

            log.info("迁移第一步：创建文件夹。执行完毕。");
        }

        if (transFailureDocs) {
            QueryWrapper<DocInfo> folderAllQueryWrapper = new QueryWrapper();
            ((QueryWrapper)((QueryWrapper)((QueryWrapper)folderAllQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("old_type", "folder")).orderByAsc("old_level");

            for(DocInfo item : this.docInfoRepository.selectList(folderAllQueryWrapper)) {
                folderMap.put(item.getOldToken(), item);
            }
        }

        log.info("迁移第二步：拷贝在线文档。");
        QueryWrapper<DocInfo> docQueryWrapper = new QueryWrapper();
        if (transFailureDocs) {
            ((QueryWrapper)((QueryWrapper)((QueryWrapper)docQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("status", DocStatusEnZh.FAILURE.getEnglish())).ne("old_type", "folder");
        } else {
            ((QueryWrapper)((QueryWrapper)docQueryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).ne("old_type", "folder");
        }

        List<DocInfo> docInfoList = new ArrayList(this.docInfoRepository.selectList(docQueryWrapper));
        Map<String, SecureLevel> secureLevelMap = new HashMap();
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        SecureLevel lowestLevel = this.driveService.getLowestLevel(userToken, this.oldLowestLevelName);
        if (Objects.nonNull(lowestLevel)) {
            List<SecureLevel> secureLevelList = this.driveService.getSecureLevelList(userToken);
            if (Objects.nonNull(secureLevelList)) {
                secureLevelList.forEach((secureLevel) -> {
                    SecureLevel var10000 = (SecureLevel)secureLevelMap.put(secureLevel.getName(), secureLevel);
                });
            }
        }

        if (!CollectionUtils.isEmpty(docInfoList)) {
            log.info("迁移第二步：拷贝云文档。共需要拷贝 {} 个文件。", docInfoList.size());

            for(DocInfo item : docInfoList) {
                if (item.getNewToken() == null) {
                    try {
                        log.info("【开始拷贝】云文档：{}，类型为：{}，Token为：{}。", new Object[]{item.getOldName(), item.getOldType(), item.getOldToken()});
                        String docSecureLevel = item.getOldSecureLabel();
                        String docExternalAccess = item.getOldExternalAccess();
                        String userTokenInner = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
                        if (!Objects.equals(docSecureLevel, this.oldLowestLevelName) && !Objects.isNull(lowestLevel)) {
                            log.info("文档 {} {} 密级为：{}，需要调整为：{}。", new Object[]{item.getOldName(), item.getOldToken(), item.getOldSecureLabel(), lowestLevel.getName()});
                            this.commonService.retry(() -> this.updateDocSecureLevel(lowestLevel.getId(), item, userTokenInner), 3, "调整文件密级失败！");
                        }

                        if (!Objects.equals(docExternalAccess, "External")) {
                            log.info("文档 {} {} 对外访问属性为：{}，需要调整为：{}。", new Object[]{item.getOldName(), item.getOldToken(), item.getOldExternalAccess(), "External"});
                            this.commonService.retry(() -> this.updateDocExternalAccess("External", item, this.oldTenantAppId, this.oldTenantAppSecret), 3, "调整对外访问属性失败！");
                        }

                        this.transferFeishuDoc(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, item);
                        if (!Objects.equals(docExternalAccess, "External")) {
                            log.info("文档 {} {} 对外访问属性恢复为 {}。", new Object[]{item.getOldName(), item.getOldToken(), item.getOldExternalAccess()});
                            this.commonService.retry(() -> this.updateDocExternalAccess(item.getOldExternalAccess(), item, this.oldTenantAppId, this.oldTenantAppSecret), 3, "对外访问属性恢复失败！");
                        }

                        if (!Objects.equals(docSecureLevel, this.oldLowestLevelName) && !"".equals(docSecureLevel) && !secureLevelMap.isEmpty()) {
                            log.info("文档 {} {} 密级恢复为：{}。", new Object[]{item.getOldName(), item.getOldToken(), item.getOldSecureLabel()});
                            this.commonService.retry(() -> this.updateDocSecureLevel(((SecureLevel)secureLevelMap.get(docSecureLevel)).getId(), item, userTokenInner), 3, "密级恢复失败！");
                        }
                    } catch (Exception e) {
                        log.error("异常：{}", e.getMessage());
                        item.setProcMsg("系统异常");
                        item.setStatus(DocStatusEnZh.FAILURE.getEnglish());
                        this.docInfoRepository.updateById(item);
                    } finally {
                        log.info("【结束拷贝】云文档：{}，类型为：{}，Token为：{}。", new Object[]{item.getOldName(), item.getOldType(), item.getOldToken()});
                    }
                }
            }
        }

        log.info("迁移第二步：拷贝云文档。执行完毕。");
    }

    private void updateDocExternalAccess(String external, DocInfo item, String appId, String appSecret) {
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, item.getOldUserOpenId());
        RequestOptions reqOptions = RequestOptions.newBuilder().userAccessToken(userToken).build();
        Client client = Client.newBuilder(appId, appSecret).openBaseUrl(BaseUrlEnum.FeiShu).requestTimeout(30L, TimeUnit.SECONDS).logReqAtDebug(true).build();
        PatchPermissionPublicReq req = PatchPermissionPublicReq.newBuilder().token(item.getOldToken()).type(item.getOldToken()).permissionPublicRequest(PermissionPublicRequest.newBuilder().externalAccess("External".equals(external)).build()).build();

        try {
            client.drive().permissionPublic().patch(req, reqOptions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDocSecureLevel(String sLevel, DocInfo item, String userToken) {
        this.driveApi.updateDocSecureLevel(userToken, item.getOldToken(), item.getOldType(), sLevel);
    }

    public String createFolder(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) {
        String procStatus = docInfo.getProcStatus();
        String procMsg = docInfo.getProcMsg();
        if (!StringUtils.equals("获取到文件夹", procStatus) && !StringUtils.equals("创建失败", procStatus)) {
            return "文件夹: " + docInfo.getOldName() + " 不需要处理";
        } else if (!StringUtils.isBlank(docInfo.getNewToken())) {
            return "文件夹: " + docInfo.getOldName() + " 不需要处理";
        } else {
            try {
                procStatus = "创建失败";
                procMsg = "文件夹创建失败，因为其父文件夹创建失败或未创建";
                String newParentFolderToken = this.getNewParentFolderToken(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, docInfo);
                procMsg = "文件夹创建失败";
                AtomicReference<Response<CreateFolderResponseDTO>> atomicResponse = new AtomicReference();
                this.commonService.retry(() -> {
                    String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
                    Response<CreateFolderResponseDTO> result = this.driveApi.createFolder(newUserAccessToken, newParentFolderToken, docInfo.getOldName());
                    atomicResponse.set(result);
                    log.info("创建文件夹的响应为：{}", (new Gson()).toJson(result));
                }, 5, "文件夹创建失败");
                CreateFolderResponseDTO data = null;
                if (atomicResponse.get() != null && (data = (CreateFolderResponseDTO)((Response)atomicResponse.get()).getData()) != null) {
                    docInfo.setNewToken(data.getToken());
                    docInfo.setNewName(docInfo.getOldName());
                    docInfo.setNewType(docInfo.getOldType());
                    docInfo.setStatus(DocStatusEnZh.SUCCESS.getEnglish());
                    procMsg = "文件夹创建成功";
                    procStatus = "创建成功";
                } else {
                    docInfo.setStatus(DocStatusEnZh.FAILURE.getEnglish());
                }
            } catch (Exception exception) {
                logger.info("文件夹：" + docInfo.getOldName() + procMsg);
                exception.printStackTrace();
                docInfo.setStatus(DocStatusEnZh.FAILURE.getEnglish());
            }

            docInfo.setProcMsg(procMsg);
            docInfo.setProcStatus(procStatus);
            this.docInfoRepository.updateById(docInfo);
            return "文件夹：" + docInfo.getOldName() + procMsg;
        }
    }

    public String getNewParentFolderToken(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) throws CustomException {
        String newParentFolderToken = null;
        if (StringUtils.equals(oldRootFolderToken, docInfo.getOldParentToken())) {
            newParentFolderToken = newRootFolderToken;
        } else {
            DocInfo parentDocInfo = (DocInfo)folderMap.get(docInfo.getOldParentToken());
            if (parentDocInfo == null) {
                throw new CustomException("父级目录不存在");
            }

            if (StringUtils.isBlank(parentDocInfo.getNewToken())) {
                this.createFolder(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, parentDocInfo);
            }

            newParentFolderToken = parentDocInfo.getNewToken();
        }

        return newParentFolderToken;
    }

    public String transferDoc(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) {
        String procStatus = docInfo.getProcStatus();
        String procMsg = docInfo.getProcMsg();
        if (!StringUtils.equals("获取到云文档", procStatus) && !StringUtils.equals("复制失败", procStatus)) {
            return "云文档：" + docInfo.getOldName() + " 不需要处理";
        } else {
            String newParentFolderToken = this.getNewParentFolderToken(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, docInfo);
            String oldDocToken = docInfo.getOldToken();
            String oldDocType = docInfo.getOldType();
            String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
            boolean addPermission = false;

            try {
                procStatus = "复制失败";
                procMsg = "文件分享授权失败";
                this.commonService.retry(() -> this.driveApi.addPermission(oldUserAccessToken, oldDocToken, oldDocType, newUserOpenId), 5, "文件分享授权");
                addPermission = true;
                AtomicReference<Response<CopyDocResponseDTO>> response = new AtomicReference();
                this.commonService.retry(() -> {
                    String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
                    response.set(this.driveApi.copyDoc(newUserAccessToken, oldDocToken, oldDocType, newParentFolderToken, docInfo.getOldName()));
                }, 5, "文档复制");
                CopyDocResponseDTO data = null;
                procMsg = "文件不存在";
                if (response.get() != null && (data = (CopyDocResponseDTO)((Response)response.get()).getData()) != null) {
                    docInfo.setNewName(docInfo.getOldName());
                    docInfo.setNewToken(data.getToken());
                    docInfo.setNewType(oldDocType);
                    procMsg = "文件复制成功";
                    procStatus = "复制成功";
                }
            } catch (Exception e) {
                logger.info("云文档：" + docInfo.getOldName() + " " + procMsg);
                e.printStackTrace();
            } finally {
                if (addPermission) {
                    try {
                        this.driveApi.removePermission(oldUserAccessToken, oldDocToken, oldDocType, newUserOpenId);
                    } catch (Exception var23) {
                        docInfo.setProcMsg("文档取消授权失败");
                    }
                }

                docInfo.setProcMsg(procMsg);
                docInfo.setProcStatus(procStatus);
                this.docInfoRepository.updateById(docInfo);
            }

            return "云文档：" + docInfo.getOldName() + " " + procMsg;
        }
    }

    public String transferFeishuDoc(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) {
        String procStatus = docInfo.getProcStatus();
        String procMsg = docInfo.getProcMsg();
        String newParentFolderToken = this.getNewParentFolderToken(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, docInfo);
        String oldDocToken = docInfo.getOldToken();
        String oldDocType = docInfo.getOldType();
        String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        boolean addPermission = false;

        try {
            procStatus = "复制失败";
            procMsg = "文件分享授权失败";
            this.commonService.retry(() -> this.driveApi.addPermission(oldUserAccessToken, oldDocToken, oldDocType, newUserOpenId), 5, "文件分享授权");
            addPermission = true;
            AtomicReference<Response<CopyDocResponseDTO>> response = new AtomicReference();
            this.commonService.retry(() -> {
                String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
                response.set(this.driveApi.copyDoc(newUserAccessToken, oldDocToken, oldDocType, newParentFolderToken, docInfo.getOldName()));
            }, 5, "文档复制");
            CopyDocResponseDTO data = null;
            procMsg = "文件不存在";
            if (response.get() != null && (data = (CopyDocResponseDTO)((Response)response.get()).getData()) != null) {
                docInfo.setNewName(docInfo.getOldName());
                docInfo.setNewToken(data.getToken());
                docInfo.setNewType(oldDocType);
                docInfo.setStatus(DocStatusEnZh.SUCCESS.getEnglish());
                procMsg = "文件复制成功";
                procStatus = "复制成功";
            } else {
                docInfo.setStatus(DocStatusEnZh.FAILURE.getEnglish());
            }
        } catch (Exception e) {
            logger.info("云文档：" + docInfo.getOldName() + " " + procMsg);
            e.printStackTrace();
            docInfo.setStatus(DocStatusEnZh.FAILURE.getEnglish());
        } finally {
            if (addPermission) {
                try {
                    this.driveApi.removePermission(oldUserAccessToken, oldDocToken, oldDocType, newUserOpenId);
                } catch (Exception var23) {
                    docInfo.setProcMsg("文档取消授权失败");
                }
            }

            docInfo.setProcMsg(procMsg);
            docInfo.setProcStatus(procStatus);
            this.docInfoRepository.updateById(docInfo);
        }

        return "云文档：" + docInfo.getOldName() + " " + procMsg;
    }

    public String transferFile(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) {
        String procStatus = docInfo.getProcStatus();
        String procMsg = docInfo.getProcMsg();
        if (!StringUtils.equals("未下载", procStatus) && !StringUtils.equals("下载成功", procStatus) && !StringUtils.equals("上传失败", procStatus)) {
            return "文件: " + docInfo.getOldName() + " 不需要处理";
        } else {
            String newParentFolderToken = this.getNewParentFolderToken(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, docInfo);
            String filepath = null;

            try {
                procStatus = "上传失败";
                procMsg = "文件下载失败";
                filepath = this.driveService.downloadFile(oldUserOpenId, docInfo);
                String newFileToken = this.driveService.uploadFile(newUserOpenId, filepath, newParentFolderToken, docInfo);
                procStatus = "上传成功";
                procMsg = "文件删除失败";
                docInfo.setNewToken(newFileToken);
                docInfo.setNewName(docInfo.getOldName());
                docInfo.setNewType(docInfo.getOldType());
            } catch (Exception e) {
                logger.info("文件：" + docInfo.getOldName() + " " + procMsg);
                e.printStackTrace();
            } finally {
                if (StringUtils.isNotBlank(filepath) && FileUtil.checkIfFileExists(filepath)) {
                    FileUtil.deleteFile(filepath);
                    procMsg = "文件删除成功";
                }

                docInfo.setProcStatus(procStatus);
                docInfo.setProcMsg(procMsg);
                this.docInfoRepository.updateById(docInfo);
            }

            return "文件：" + docInfo.getOldName() + " " + procMsg;
        }
    }

    public String transferFeishuFile(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, SecureLevel lowestLevel, Map<String, SecureLevel> secureLevelMap, DocInfo docInfo) {
        String procStatus = docInfo.getProcStatus();
        String procMsg = docInfo.getProcMsg();
        if (!StringUtils.equals("未下载", procStatus) && !StringUtils.equals("下载成功", procStatus) && !StringUtils.equals("上传失败", procStatus)) {
            return "文件: " + docInfo.getOldName() + " 不需要处理";
        } else {
            String newParentFolderToken = this.getNewParentFolderToken(oldRootFolderToken, newRootFolderToken, oldUserOpenId, newUserOpenId, folderMap, docInfo);
            String filepath = null;
            String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);

            try {
                if (!Objects.equals(docInfo.getOldSecureLabel(), this.oldLowestLevelName) && !Objects.isNull(lowestLevel)) {
                    log.info("文件 {} {} 密级为：{}，需要调整为：{}。", new Object[]{docInfo.getOldName(), docInfo.getOldToken(), docInfo.getOldSecureLabel(), lowestLevel.getName()});
                    this.commonService.retry(() -> this.updateDocSecureLevel(lowestLevel.getId(), docInfo, userToken), 3, "调整文件密级失败！");
                }

                if (!Objects.equals(docInfo.getOldExternalAccess(), "External")) {
                    log.info("文件 {} {} 对外访问属性为：{}，需要调整为：{}。", new Object[]{docInfo.getOldName(), docInfo.getOldToken(), docInfo.getOldExternalAccess(), "External"});
                    this.commonService.retry(() -> this.updateDocExternalAccess("External", docInfo, this.oldTenantAppId, this.oldTenantAppSecret), 3, "调整对外访问属性失败！");
                }

                procStatus = "上传失败";
                procMsg = "文件下载失败";
                filepath = this.driveService.downloadFile(oldUserOpenId, docInfo);
                String newFileToken = this.driveService.uploadFile(newUserOpenId, filepath, newParentFolderToken, docInfo);
                procStatus = "上传成功";
                procMsg = "文件删除失败";
                docInfo.setNewToken(newFileToken);
                docInfo.setNewName(docInfo.getOldName());
                docInfo.setNewType(docInfo.getOldType());
                docInfo.setStatus(DocStatusEnZh.SUCCESS.getEnglish());
            } catch (Exception e) {
                logger.info("文件：" + docInfo.getOldName() + " " + procMsg);
                e.printStackTrace();
                docInfo.setStatus(DocStatusEnZh.FAILURE.getEnglish());
            } finally {
                if (StringUtils.isNotBlank(filepath) && FileUtil.checkIfFileExists(filepath)) {
                    FileUtil.deleteFile(filepath);
                    procMsg = "文件删除成功";
                }

                try {
                    if (!Objects.equals(docInfo.getOldExternalAccess(), "External")) {
                        log.info("文件 {} {} 对外访问属性恢复为 {}。", new Object[]{docInfo.getOldName(), docInfo.getOldToken(), docInfo.getOldExternalAccess()});
                        this.commonService.retry(() -> this.updateDocExternalAccess(docInfo.getOldExternalAccess(), docInfo, this.oldTenantAppId, this.oldTenantAppSecret), 3, "对外访问属性恢复失败！");
                    }

                    if (!Objects.equals(docInfo.getOldSecureLabel(), this.oldLowestLevelName) && !"".equals(docInfo.getOldSecureLabel()) && !secureLevelMap.isEmpty()) {
                        log.info("文件 {} {} 密级恢复为：{}。", new Object[]{docInfo.getOldName(), docInfo.getOldToken(), docInfo.getOldSecureLabel()});
                        this.commonService.retry(() -> this.updateDocSecureLevel(((SecureLevel)secureLevelMap.get(docInfo.getOldSecureLabel())).getId(), docInfo, userToken), 3, "密级恢复失败！");
                    }
                } catch (Exception e) {
                    log.error("文件处理异常：{}", e.getMessage());
                    procStatus = "文件处理异常";
                    docInfo.setStatus(DocStatusEnZh.FAILURE.getEnglish());
                }

                docInfo.setProcStatus(procStatus);
                docInfo.setProcMsg(procMsg);
                this.docInfoRepository.updateById(docInfo);
            }

            return "文件：" + docInfo.getOldName() + " " + procMsg;
        }
    }

    public UserDTO getDocMember(String oldUserOpenId, String docToken) {
        String userAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        Response<QueryDocMemberResponseDTO> response = this.driveApi.queryMemberList(userAccessToken, docToken, "doc");
        QueryDocMemberResponseDTO body = null;
        List<DriveMemberDTO> memberList = null;
        if (response != null && (body = (QueryDocMemberResponseDTO)response.getData()) != null && (memberList = body.getMembers()) != null && memberList.size() > 1) {
            for(DriveMemberDTO member : memberList) {
                String openId = member.getMemberOpenId();
                if (!oldUserOpenId.equals(openId)) {
                    String userId = member.getMemberUserId();
                    String appAccessToken = this.accessTokenService.getTenantAccessToken(TenantType.NEW);
                    return this.userService.getUserById(UserIdTypeEnum.USER_ID, appAccessToken, userId);
                }
            }
        }

        throw new CustomException("协作者不存在");
    }

    private LinkedList<FolderChildDTO> getFolderChildrenFromLark(String oldUserOpenId, String folderToken) {
        String userAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        Response<FolderChildrenDTO> response = this.driveApi.queryFolderChildrenInfo(userAccessToken, folderToken);
        FolderChildrenDTO folderChildrenInfo = null;
        LinkedList<FolderChildDTO> folderChildrenList = new LinkedList();
        if (response != null && (folderChildrenInfo = (FolderChildrenDTO)response.getData()) != null) {
            Map<String, FolderChildDTO> childrenMap = folderChildrenInfo.getChildren();
            if (childrenMap != null) {
                for(Map.Entry<String, FolderChildDTO> entry : childrenMap.entrySet()) {
                    folderChildrenList.add(entry.getValue());
                }
            }
        }

        return folderChildrenList;
    }

    public TransferResultDTO getResult(String oldUserOpenId, String newUserOpenId) {
        QueryWrapper<DocInfo> queryWrapper = new QueryWrapper();
        ((QueryWrapper)queryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
        List<DocInfo> docInfoList = this.docInfoRepository.selectList(queryWrapper);
        TransferResultDTO result = TransferResultDTO.builder().total(docInfoList.size()).build();

        for(DocInfo item : docInfoList) {
            String docType = item.getOldType();
            String procStatus = item.getProcStatus();
            String procMsg = item.getProcMsg();
            String status = item.getStatus();
            if (docType.contains("folder")) {
                result.setFolder(result.getFolder() + 1);
                if (status.equals(DocStatusEnZh.PENDING.getEnglish())) {
                    result.setFolderNone(result.getFolderNone() + 1);
                } else if (status.equals(DocStatusEnZh.SUCCESS.getEnglish())) {
                    result.setFolderDone(result.getFolderDone() + 1);
                } else {
                    result.setFolderFail(result.getFolderFail() + 1);
                }
            } else if (docType.contains("file")) {
                result.setFile(result.getFile() + 1);
                if (status.equals(DocStatusEnZh.PENDING.getEnglish())) {
                    result.setFileNone(result.getFileNone() + 1);
                }

                if (status.equals(DocStatusEnZh.SUCCESS.getEnglish())) {
                    result.setFileDone(result.getFileDone() + 1);
                }

                if (status.equals(DocStatusEnZh.FAILURE.getEnglish())) {
                    result.setFileFail(result.getFileFail() + 1);
                }
            } else if (docType.contains("bitable")) {
                result.setBitTable(result.getBitTable() + 1);
                if (status.equals(DocStatusEnZh.PENDING.getEnglish())) {
                    result.setBitTableNone(result.getBitTableNone() + 1);
                }

                if (status.equals(DocStatusEnZh.SUCCESS.getEnglish())) {
                    result.setBitTableDone(result.getBitTableDone() + 1);
                }

                if (status.equals(DocStatusEnZh.FAILURE.getEnglish())) {
                    result.setBitTableFail(result.getBitTableFail() + 1);
                }
            } else if (docType.contains("sheet")) {
                result.setSheet(result.getSheet() + 1);
                if (status.equals(DocStatusEnZh.PENDING.getEnglish())) {
                    result.setSheetNone(result.getSheetNone() + 1);
                }

                if (status.equals(DocStatusEnZh.SUCCESS.getEnglish())) {
                    result.setSheetDone(result.getSheetDone() + 1);
                }

                if (status.equals(DocStatusEnZh.FAILURE.getEnglish())) {
                    result.setSheetFail(result.getSheetFail() + 1);
                }
            } else if (docType.contains("doc")) {
                result.setDoc(result.getDoc() + 1);
                if (status.equals(DocStatusEnZh.PENDING.getEnglish())) {
                    result.setDocNone(result.getDocNone() + 1);
                }

                if (status.equals(DocStatusEnZh.SUCCESS.getEnglish())) {
                    result.setDocDone(result.getDocDone() + 1);
                }

                if (status.equals(DocStatusEnZh.FAILURE.getEnglish())) {
                    result.setDocFail(result.getDocFail() + 1);
                }
            } else {
                result.setOtherOnline(result.getOtherOnline() + 1);
                if (status.equals(DocStatusEnZh.FAILURE.getEnglish())) {
                    result.setOtherOnlineFail(result.getOtherOnlineFail() + 1);
                } else if (status.equals(DocStatusEnZh.SUCCESS.getEnglish())) {
                    result.setOtherOnlineDone(result.getOtherOnlineDone() + 1);
                } else {
                    result.setOtherOnlineNone(result.getOtherOnlineNone() + 1);
                }
            }
        }

        return result;
    }

    public void batchSaveDocs(List<DocInfo> list) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH, false);

        try {
            DocInfoRepository docInfoRepository = (DocInfoRepository)sqlSession.getMapper(DocInfoRepository.class);
            list.stream().forEach((user) -> docInfoRepository.insert(user));
            sqlSession.commit();
            sqlSession.rollback();
        } catch (Exception var7) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

    }

    public Integer docCount(QueryWrapper<DocInfo> queryWrapper) {
        return this.docInfoRepository.selectCount(queryWrapper);
    }

    class CreateFolderTask implements Runnable {
        private String oldRootFolderToken;
        private String newRootFolderToken;
        private String oldUserOpenId;
        private String newUserOpenId;
        private Map<String, DocInfo> folderMap;
        private DocInfo docInfo;

        public CreateFolderTask(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) {
            this.oldRootFolderToken = oldRootFolderToken;
            this.newRootFolderToken = newRootFolderToken;
            this.oldUserOpenId = oldUserOpenId;
            this.newUserOpenId = newUserOpenId;
            this.folderMap = folderMap;
            this.docInfo = docInfo;
        }

        public void run() {
            String procStatus = this.docInfo.getProcStatus();
            String procMsg = this.docInfo.getProcMsg();

            try {
                procStatus = "创建失败";
                procMsg = "文件夹创建失败，因为其父文件夹创建失败或未创建";
                String newParentFolderToken = DocInfoServiceImpl.this.getNewParentFolderToken(this.oldRootFolderToken, this.newRootFolderToken, this.oldUserOpenId, this.newUserOpenId, this.folderMap, this.docInfo);
                procMsg = "文件夹创建失败";
                AtomicReference<Response<CreateFolderResponseDTO>> atomicResponse = new AtomicReference();
                DocInfoServiceImpl.this.commonService.retry(() -> {
                    String newUserAccessToken = DocInfoServiceImpl.this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, this.newUserOpenId);
                    Response<CreateFolderResponseDTO> result = DocInfoServiceImpl.this.driveApi.createFolder(newUserAccessToken, newParentFolderToken, this.docInfo.getOldName());
                    atomicResponse.set(result);
                }, 5, "文件夹创建失败");
                CreateFolderResponseDTO data = null;
                if (atomicResponse.get() != null && (data = (CreateFolderResponseDTO)((Response)atomicResponse.get()).getData()) != null) {
                    this.docInfo.setNewToken(data.getToken());
                    this.docInfo.setNewName(this.docInfo.getOldName());
                    this.docInfo.setNewType(this.docInfo.getOldType());
                    procMsg = "文件夹创建成功";
                    procStatus = "创建成功";
                }
            } catch (Exception exception) {
                DocInfoServiceImpl.logger.info("文件夹：" + this.docInfo.getOldName() + procMsg);
                exception.printStackTrace();
            }

            this.docInfo.setProcMsg(procMsg);
            this.docInfo.setProcStatus(procStatus);
            DocInfoServiceImpl.this.docInfoRepository.updateById(this.docInfo);
            this.folderMap.put(this.docInfo.getOldToken(), this.docInfo);
        }
    }
}
