package com.lark.service.impl;

import com.lark.api.auth.AccessTokenApi;
import com.lark.data.bean.Response;
import com.lark.data.constants.TenantType;
import com.lark.data.dto.token.TenantAccessTokenDTO;
import com.lark.data.dto.token.UserAccessTokenDTO;
import com.lark.exception.CustomException;
import com.lark.service.AccessTokenService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final String tenantAccessTokenKey = "tenant_access_token:";
    private final String userAccessTokenKey = "user_access_token:";
    private final String userRefreshTokenKey = "user_refresh_token:";
    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);
    @Value("${lark.old.appId}")
    private String oldAppId;
    @Value("${lark.old.appSecret}")
    private String oldAppSecret;
    @Value("${lark.new.appId}")
    private String newAppId;
    @Value("${lark.new.appSecret}")
    private String newAppSecret;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AccessTokenApi accessTokenApi;

    public AccessTokenServiceImpl() {
    }

    public String getTenantAccessToken(TenantType tenantType) throws CustomException {
        String appId = TenantType.NEW == tenantType ? this.newAppId : this.oldAppId;
        StringBuilder var10000 = new StringBuilder();
        this.getClass();
        String key = var10000.append("tenant_access_token:").append(TenantType.NEW == tenantType ? "new" : "old").append(":").append(appId).toString();
        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        Object value = operations.get(key);
        return value != null ? (String)value : this.refreshTenantAccessToken(tenantType);
    }

    public String refreshTenantAccessToken(TenantType tenantType) throws CustomException {
        String appId = TenantType.NEW == tenantType ? this.newAppId : this.oldAppId;
        String appSecret = TenantType.NEW == tenantType ? this.newAppSecret : this.oldAppSecret;
        logger.info("appId:" + appId);
        logger.info("appSecret:" + appSecret);
        TenantAccessTokenDTO response = this.accessTokenApi.getTenantAccessTokenResponse(appId, appSecret);
        String tenantAccessToken = null;
        if (response != null && (tenantAccessToken = response.getTenantAccessToken()) != null) {
            StringBuilder var10000 = new StringBuilder();
            this.getClass();
            String key = var10000.append("tenant_access_token:").append(TenantType.NEW == tenantType ? "new" : "old").append(":").append(appId).toString();
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            long expire = (long)(response.getExpire() - 600);
            operations.set(key, tenantAccessToken, expire, TimeUnit.SECONDS);
            return tenantAccessToken;
        } else {
            throw new CustomException("获取飞书 app_access_token 失败");
        }
    }

    public UserAccessTokenDTO getUserAccessTokenByCode(TenantType tenantType, String code) throws CustomException {
        String appAccessToken = this.getTenantAccessToken(tenantType);
        logger.info("appAccessToken：" + appAccessToken);
        Response<UserAccessTokenDTO> response = this.accessTokenApi.getUserAccessTokenInfoResponse(appAccessToken, code);
        return this.getUserAccessToken(response, "获取用户 user_access_token 失败");
    }

    public UserAccessTokenDTO refreshUserAccessToken(TenantType tenantType, String refreshAccessToken) {
        String appAccessToken = this.getTenantAccessToken(tenantType);
        Response<UserAccessTokenDTO> response = this.accessTokenApi.refreshUserAccessTokenInfoResponse(appAccessToken, refreshAccessToken);
        return this.getUserAccessToken(response, "刷新用户 user_access_token 失败");
    }

    private UserAccessTokenDTO getUserAccessToken(Response<UserAccessTokenDTO> response, String errorMsg) {
        UserAccessTokenDTO userAccessToken = null;
        if (response != null && (userAccessToken = (UserAccessTokenDTO)response.getData()) != null) {
            StringBuilder var10000 = new StringBuilder();
            this.getClass();
            String accessTokenKey = var10000.append("user_access_token:").append(userAccessToken.getOpenId()).toString();
            var10000 = new StringBuilder();
            this.getClass();
            String refreshTokenKey = var10000.append("user_refresh_token:").append(userAccessToken.getOpenId()).toString();
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            int accessTokenExpire = userAccessToken.getExpiresIn() - 600;
            int refreshTokenExpire = userAccessToken.getRefreshExpiresIn() - 600;
            operations.set(accessTokenKey, userAccessToken.getAccessToken(), (long)accessTokenExpire, TimeUnit.SECONDS);
            operations.set(refreshTokenKey, userAccessToken.getRefreshToken(), (long)refreshTokenExpire, TimeUnit.SECONDS);
            return userAccessToken;
        } else {
            throw new CustomException(errorMsg);
        }
    }
}
