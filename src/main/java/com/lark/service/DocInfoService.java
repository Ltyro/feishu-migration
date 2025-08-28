package com.lark.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lark.data.dto.contact.UserDTO;
import com.lark.data.dto.doc.DocDTO;
import com.lark.data.dto.doc.TransferResultDTO;
import com.lark.data.pojo.DocInfo;
import com.lark.exception.CustomException;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Async;

public interface DocInfoService {
    DocDTO createDoc(String oldUserOpenId);

    @Async
    void startTransferDocs(String oldUserOpenId, String newUserOpenId);

    @Async
    void restartTransferDocs(String oldUserOpenId, String newUserOpenId);

    void getDocInfoFromLark(String oldUserOpenId, String newUserOpenId);

    void copyDocInfoToNewUser(String oldUserOpenId, String newUserOpenId);

    void transferDocsInfoToNewUser(String oldUserOpenId, String newUserOpenId, boolean transFailureDocs);

    Integer docCount(QueryWrapper<DocInfo> queryWrapper);

    String createFolder(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo);

    String getNewParentFolderToken(String oldRootFolderToken, String newRootFolderToken, String oldUserOpenId, String newUserOpenId, Map<String, DocInfo> folderMap, DocInfo docInfo) throws CustomException;

    UserDTO getDocMember(String oldUserOpenId, String docToken);

    TransferResultDTO getResult(String oldUserOpenId, String newUserOpenId);

    void batchSaveDocs(List<DocInfo> list);
}
