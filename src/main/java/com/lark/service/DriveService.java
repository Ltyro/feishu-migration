package com.lark.service;

import com.lark.data.constants.TenantType;
import com.lark.data.dto.drive.SecureLevel;
import com.lark.data.pojo.DocInfo;
import java.io.IOException;
import java.util.List;

public interface DriveService {
    String getRootFolderToken(TenantType tenantType, String openId);

    String downloadFile(String oldUserOpenId, DocInfo docInfo) throws IOException;

    String uploadFile(String newUserOpenId, String filepath, String parentFileToken, DocInfo docInfo) throws IOException, InterruptedException;

    SecureLevel getLowestLevel(String userToken, String lowestLevelName);

    List<SecureLevel> getSecureLevelList(String userToken);
}
