package com.lark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lark.api.sheet.SheetApi;
import com.lark.data.bean.Response;
import com.lark.data.constants.DocStatusEnZh;
import com.lark.data.constants.TenantType;
import com.lark.data.constants.TenantTypeEnZh;
import com.lark.data.dto.sheet.CreateSheetResponseDTO;
import com.lark.data.dto.sheet.SheetDTO;
import com.lark.data.dto.sheet.SheetMetaInfoDTO;
import com.lark.data.pojo.DocInfo;
import com.lark.data.pojo.TransferResult;
import com.lark.data.pojo.UserInfo;
import com.lark.exception.CustomException;
import com.lark.service.SheetService;
import com.lark.service.UserInfoService;
import com.lark.service.repository.TransferResultRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SheetServiceImpl implements SheetService {
    @Autowired
    private SheetApi sheetApi;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TransferResultRepository transferResultRepository;

    public SheetServiceImpl() {
    }

    public String getResultSheetUrl(String oldUserOpenId, String newUserOpenId, List<DocInfo> docList) {
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        QueryWrapper<TransferResult> resultQuery = new QueryWrapper();
        ((QueryWrapper)((QueryWrapper)resultQuery.eq("old_user_open_id", oldUserOpenId)).eq("tenant_type", TenantTypeEnZh.NEW.getEnglish())).eq("new_user_open_id", newUserOpenId);
        TransferResult transferResult = (TransferResult)this.transferResultRepository.selectOne(resultQuery);
        String url = transferResult == null ? null : transferResult.getUrl();
        String sheetToken = transferResult == null ? null : transferResult.getToken();
        SheetMetaInfoDTO sheetMetaInfo = null;
        if (sheetToken == null || (sheetMetaInfo = this.getMetaInfo(newUserOpenId, sheetToken)) == null) {
            CreateSheetResponseDTO createSheetResponse = this.createSheet(newUserOpenId, userInfo.getNewRootFolderToken());
            url = createSheetResponse.getUrl();
            sheetToken = createSheetResponse.getSpreadSheetToken();
            sheetMetaInfo = this.getMetaInfo(newUserOpenId, sheetToken);
        }

        String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
        String sheetId = ((SheetDTO)sheetMetaInfo.getSheets().get(0)).getSheetId();
        int rowCount = ((SheetDTO)sheetMetaInfo.getSheets().get(0)).getRowCount();
        if (rowCount > 1) {
            this.sheetApi.deleteDimensionRange(newUserAccessToken, sheetToken, sheetId, 1, rowCount - 1);
        }

        int size = docList.size();
        this.sheetApi.addDimensionRange(newUserAccessToken, sheetToken, sheetId, size);
        this.appendData(newUserOpenId, sheetId, sheetToken, docList);
        TransferResult result = TransferResult.builder().sheetId(sheetId).oldUserOpenId(oldUserOpenId).newUserOpenId(newUserOpenId).tenantType(TenantTypeEnZh.NEW.getEnglish()).url(url).token(sheetToken).build();
        if (transferResult == null) {
            this.transferResultRepository.insert(result);
        } else {
            result.setId(transferResult.getId());
            this.transferResultRepository.updateById(result);
        }

        return url;
    }

    public String getResultSheetOldUrl(String oldUserOpenId, String newUserOpenId, List<DocInfo> docList) {
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        QueryWrapper<TransferResult> resultQuery = new QueryWrapper();
        ((QueryWrapper)((QueryWrapper)resultQuery.eq("old_user_open_id", oldUserOpenId)).eq("tenant_type", TenantTypeEnZh.OLD.getEnglish())).eq("new_user_open_id", newUserOpenId);
        TransferResult transferResult = (TransferResult)this.transferResultRepository.selectOne(resultQuery);
        String url = transferResult == null ? null : transferResult.getUrl();
        String sheetToken = transferResult == null ? null : transferResult.getToken();
        SheetMetaInfoDTO sheetMetaInfo = null;
        if (sheetToken == null || (sheetMetaInfo = this.getMetaInfo(oldUserOpenId, sheetToken)) == null) {
            CreateSheetResponseDTO createSheetResponse = this.createSheetOld(oldUserOpenId, userInfo.getOldRootFolderToken());
            url = createSheetResponse.getUrl();
            sheetToken = createSheetResponse.getSpreadSheetToken();
            sheetMetaInfo = this.getOldMetaInfo(oldUserOpenId, sheetToken);
        }

        String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        String sheetId = ((SheetDTO)sheetMetaInfo.getSheets().get(0)).getSheetId();
        int rowCount = ((SheetDTO)sheetMetaInfo.getSheets().get(0)).getRowCount();
        if (rowCount > 1) {
            this.sheetApi.deleteDimensionRange(oldUserAccessToken, sheetToken, sheetId, 1, rowCount - 1);
        }

        int size = docList.size();
        this.sheetApi.addDimensionRange(oldUserAccessToken, sheetToken, sheetId, size);
        this.appendDataOld(oldUserOpenId, sheetId, sheetToken, docList);
        TransferResult result = TransferResult.builder().sheetId(sheetId).oldUserOpenId(oldUserOpenId).newUserOpenId(newUserOpenId).tenantType(TenantTypeEnZh.OLD.getEnglish()).url(url).token(sheetToken).build();
        if (transferResult == null) {
            this.transferResultRepository.insert(result);
        } else {
            result.setId(transferResult.getId());
            this.transferResultRepository.updateById(result);
        }

        return url;
    }

    public CreateSheetResponseDTO createSheet(String newUserOpenId, String newRootFolderToken) {
        String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
        Response<Map<String, CreateSheetResponseDTO>> response = this.sheetApi.create(newUserAccessToken, newRootFolderToken, "云文档迁移结果表");
        CreateSheetResponseDTO createSheetResponse = null;
        if (response != null && response.getData() != null && (createSheetResponse = (CreateSheetResponseDTO)((Map)response.getData()).get("spreadsheet")) != null) {
            return createSheetResponse;
        } else {
            throw new CustomException("文档创建失败");
        }
    }

    public CreateSheetResponseDTO createSheetOld(String oldUserOpenId, String oldRootFolderToken) {
        String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        Response<Map<String, CreateSheetResponseDTO>> response = this.sheetApi.create(oldUserAccessToken, oldRootFolderToken, "云文档迁移结果表");
        CreateSheetResponseDTO createSheetResponse = null;
        if (response != null && response.getData() != null && (createSheetResponse = (CreateSheetResponseDTO)((Map)response.getData()).get("spreadsheet")) != null) {
            return createSheetResponse;
        } else {
            throw new CustomException("云文档迁移结果表：文档创建失败！");
        }
    }

    public SheetMetaInfoDTO getMetaInfo(String newUserOpenId, String sheetToken) {
        String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
        Response<SheetMetaInfoDTO> response = this.sheetApi.getMetaInfo(newUserAccessToken, sheetToken);
        SheetMetaInfoDTO metaInfo = null;
        return response != null && (metaInfo = (SheetMetaInfoDTO)response.getData()) != null ? metaInfo : null;
    }

    public SheetMetaInfoDTO getOldMetaInfo(String oldUserOpenId, String sheetToken) {
        String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        Response<SheetMetaInfoDTO> response = this.sheetApi.getMetaInfo(oldUserAccessToken, sheetToken);
        SheetMetaInfoDTO metaInfo = null;
        return response != null && (metaInfo = (SheetMetaInfoDTO)response.getData()) != null ? metaInfo : null;
    }

    public void appendData(String newUserOpenId, String sheetId, String sheetToken, List<DocInfo> docList) {
        List<List<String>> data = new ArrayList();
        data.add(Arrays.asList("文件名", "文件类型", "迁移状态", "状态说明", "旧文件链接", "新文件链接"));

        for(DocInfo item : docList) {
            String name = item.getOldName();
            String type = item.getOldType();
            String status = item.getStatus();
            String successMsg = item.getProcStatus();
            String failureMsg = item.getProcMsg();
            String msg = "";
            if (DocStatusEnZh.FAILURE.getEnglish().equals(status)) {
                msg = failureMsg;
            }

            if (DocStatusEnZh.SUCCESS.getEnglish().equals(status)) {
                msg = successMsg;
            }

            String oldUrl = this.getDocUrl(type, item.getOldToken());
            String newUrl = this.getDocUrl(type, item.getNewToken());
            data.add(Arrays.asList(name, type, status, msg, oldUrl, newUrl));
        }

        String newUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId);
        String valueRange = sheetId + "!A1:F" + data.size();
        this.sheetApi.appendRows(newUserAccessToken, sheetToken, valueRange, data);
    }

    public void appendDataOld(String oldUserOpenId, String sheetId, String sheetToken, List<DocInfo> docList) {
        List<List<String>> data = new ArrayList();
        data.add(Arrays.asList("文件名", "文件类型", "迁移状态", "状态说明", "旧文件链接", "新文件链接"));

        for(DocInfo item : docList) {
            String name = item.getOldName();
            String type = item.getOldType();
            String status = item.getStatus();
            String successMsg = item.getProcStatus();
            String failureMsg = item.getProcMsg();
            String msg = "";
            if (DocStatusEnZh.FAILURE.getEnglish().equals(status)) {
                msg = failureMsg;
            }

            if (DocStatusEnZh.SUCCESS.getEnglish().equals(status)) {
                msg = successMsg;
            }

            String oldUrl = this.getDocUrl(type, item.getOldToken());
            String newUrl = this.getDocUrl(type, item.getNewToken());
            data.add(Arrays.asList(name, type, status, msg, oldUrl, newUrl));
        }

        String oldUserAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        String valueRange = sheetId + "!A1:F" + data.size();
        this.sheetApi.appendRows(oldUserAccessToken, sheetToken, valueRange, data);
    }

    private String getDocUrl(String type, String token) {
        if (token == null) {
            return "";
        } else {
            String url = null;
            return StringUtils.equals("folder", type) ? "https://www.feishu.cn/drive/" + type + "/" + token : "https://www.feishu.cn/" + type + "/" + token;
        }
    }
}
