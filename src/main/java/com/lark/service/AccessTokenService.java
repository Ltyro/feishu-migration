package com.lark.service;

import com.lark.data.constants.TenantType;
import com.lark.data.dto.token.UserAccessTokenDTO;
import com.lark.exception.CustomException;
import java.io.IOException;

public interface AccessTokenService {
    String getTenantAccessToken(TenantType tenantType);

    String refreshTenantAccessToken(TenantType tenantType) throws CustomException, IOException;

    UserAccessTokenDTO getUserAccessTokenByCode(TenantType tenantType, String code) throws CustomException;

    UserAccessTokenDTO refreshUserAccessToken(TenantType tenantType, String refreshAccessToken);
}
