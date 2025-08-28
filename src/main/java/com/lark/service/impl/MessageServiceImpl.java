package com.lark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lark.api.message.MessageApi;
import com.lark.data.constants.DocStatusEnZh;
import com.lark.data.constants.TenantType;
import com.lark.data.dto.message.SendMessageRequest;
import com.lark.data.pojo.DocInfo;
import com.lark.service.AccessTokenService;
import com.lark.service.MessageService;
import com.lark.service.SheetService;
import com.lark.service.repository.DocInfoRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    private MessageApi messageApi;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private SheetService sheetService;
    @Autowired
    private DocInfoRepository docInfoRepository;

    public MessageServiceImpl() {
    }

    public void sendMessage(TenantType tenantType, String content, String userId) {
        try {
            String tenantAccessToken = this.accessTokenService.getTenantAccessToken(tenantType);
            SendMessageRequest request = SendMessageRequest.builder().receiveId(userId).content(content).build();
            this.messageApi.sendMessage(tenantAccessToken, request);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void sendResultNotification(String oldUserOpenId, String newUserOpenId) {
        QueryWrapper<DocInfo> queryWrapper = new QueryWrapper();
        ((QueryWrapper)queryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
        List<DocInfo> docInfoList = this.docInfoRepository.selectList(queryWrapper);
        List<String> failedFileNames = new ArrayList();

        for(DocInfo docInfo : docInfoList) {
            if (docInfo.getNewToken() == null) {
                failedFileNames.add(docInfo.getOldName());
            }
        }

        String msg = "文档迁移完成，" + (CollectionUtils.isEmpty(failedFileNames) ? "全部成功" : "迁移失败的文件名为：\n" + String.join("\n", failedFileNames));

        try {
            String url = this.sheetService.getResultSheetUrl(oldUserOpenId, newUserOpenId, docInfoList);
            this.sendMessage(TenantType.NEW, "文档迁移完成，迁移结果文档为：" + url, newUserOpenId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void sendTransNotification(String oldUserOpenId, String newUserOpenId) {
        QueryWrapper<DocInfo> queryWrapper = new QueryWrapper();
        ((QueryWrapper)queryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
        List<DocInfo> docInfoList = this.docInfoRepository.selectList(queryWrapper);
        List<String> failedFileNames = new ArrayList();

        for(DocInfo docInfo : docInfoList) {
            if (DocStatusEnZh.FAILURE.getEnglish().equals(docInfo.getStatus())) {
                failedFileNames.add(docInfo.getOldName());
            }
        }

        String msg = "文档迁移完成，" + (CollectionUtils.isEmpty(failedFileNames) ? "全部成功" : "迁移失败的文件名为：\n" + String.join("\n", failedFileNames));

        try {
            String newTenantResultUrl = this.sheetService.getResultSheetUrl(oldUserOpenId, newUserOpenId, docInfoList);
            this.sendMessage(TenantType.NEW, "文档迁移完成，迁移结果文档为：" + newTenantResultUrl, newUserOpenId);
            String oldTenantResultUrl = this.sheetService.getResultSheetOldUrl(oldUserOpenId, newUserOpenId, docInfoList);
            this.sendMessage(TenantType.OLD, "文档迁移完成，迁移结果文档为：" + oldTenantResultUrl, oldUserOpenId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
