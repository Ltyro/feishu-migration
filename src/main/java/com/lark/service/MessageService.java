package com.lark.service;

import com.lark.data.constants.TenantType;

public interface MessageService {
    void sendMessage(TenantType tenantType, String content, String userId);

    void sendResultNotification(String oldUserOpenId, String newUserOpenId);

    void sendTransNotification(String oldUserOpenId, String newUserOpenId);
}
