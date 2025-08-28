package com.lark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lark.data.constants.TenantType;
import com.lark.data.dto.token.UserAccessTokenDTO;
import com.lark.data.pojo.UserInfo;
import com.lark.exception.CustomException;
import com.lark.service.AccessTokenService;
import com.lark.service.UserInfoService;
import com.lark.service.repository.UserRepository;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final String userAccessTokenKey = "user_access_token:";
    private final String userRefreshTokenKey = "user_refresh_token:";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private UserRepository userRepository;

    public UserInfoServiceImpl() {
    }

    public UserInfo queryUserInfoByOpenId(String openId) {
        return this.queryUserInfoByOpenId(TenantType.OLD, openId);
    }

    public UserInfo queryUserInfoByOpenId(TenantType tenantType, String openId) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        String openIdColumn = TenantType.NEW == tenantType ? "new_user_open_id" : "old_user_open_id";
        queryWrapper.eq(openIdColumn, openId);
        return (UserInfo)this.userRepository.selectOne(queryWrapper);
    }

    public UserInfo queryUserInfoByOpenId(String oldUserOpenId, String newUserOpenId) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        ((QueryWrapper)queryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
        return (UserInfo)this.userRepository.selectOne(queryWrapper);
    }

    public List<UserInfo> queryUserInfoByOldUserOpenId(String oldUserOpenId) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("old_user_open_id", oldUserOpenId);
        return this.userRepository.selectList(queryWrapper);
    }

    public List<UserInfo> queryUsers(QueryWrapper<UserInfo> queryWrapper) {
        return this.userRepository.selectList(queryWrapper);
    }

    public String queryUserAccessTokenByOpenId(TenantType tenantType, String openId) {
        StringBuilder var10000 = new StringBuilder();
        this.getClass();
        String accessTokenKey = var10000.append("user_access_token:").append(openId).toString();
        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        if (operations.get(accessTokenKey) != null) {
            return (String)operations.get(accessTokenKey);
        } else {
            var10000 = new StringBuilder();
            this.getClass();
            String refreshTokenKey = var10000.append("user_refresh_token:").append(openId).toString();
            String refreshToken = null;
            if (operations.get(refreshTokenKey) != null) {
                refreshToken = (String)operations.get(refreshTokenKey);
                UserAccessTokenDTO userAccessToken = this.accessTokenService.refreshUserAccessToken(tenantType, refreshToken);
                this.updateUserAccessTokenAndRefreshToken(tenantType, openId, userAccessToken.getAccessToken(), userAccessToken.getRefreshToken());
                return userAccessToken.getAccessToken();
            } else {
                throw new CustomException("用户AccessToken获取失败，请重新登陆");
            }
        }
    }

    public String queryUserRefreshTokenByOpenId(TenantType tenantType, String openId) {
        StringBuilder var10000 = new StringBuilder();
        this.getClass();
        String accessTokenKey = var10000.append("user_refresh_token:").append(openId).toString();
        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        return operations.get(accessTokenKey) != null ? (String)operations.get(accessTokenKey) : null;
    }

    public int updateUserInfo(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        ((QueryWrapper)queryWrapper.eq("old_user_open_id", userInfo.getOldUserOpenId())).eq("new_user_open_id", userInfo.getNewUserOpenId());
        return this.userRepository.update(userInfo, queryWrapper);
    }

    public int insertUserInfo(UserInfo userInfo) {
        return this.userRepository.insert(userInfo);
    }

    public void replaceUserOpenId(String oldUserOpenId, String newUserOpenId, String replaceNewUserOpenId) {
        UserInfo userInfo = this.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        StringBuilder var10000 = new StringBuilder();
        this.getClass();
        String accessTokenKey = var10000.append("user_access_token:").append(newUserOpenId).toString();
        var10000 = new StringBuilder();
        this.getClass();
        String newAccessTokenKey = var10000.append("user_access_token:").append(replaceNewUserOpenId).toString();
        this.replaceCacheKey(accessTokenKey, newAccessTokenKey);
        var10000 = new StringBuilder();
        this.getClass();
        String refreshTokenKey = var10000.append("user_refresh_token:").append(newUserOpenId).toString();
        var10000 = new StringBuilder();
        this.getClass();
        String newRefreshTokenKey = var10000.append("user_refresh_token:").append(replaceNewUserOpenId).toString();
        this.replaceCacheKey(refreshTokenKey, newRefreshTokenKey);
        userInfo.setNewUserOpenId(replaceNewUserOpenId);
        this.updateUserInfo(userInfo);
    }

    private void replaceCacheKey(String originKey, String newKey) {
        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        Object data = operations.get(originKey);
        if (data != null) {
            Long expire = this.redisTemplate.getExpire(originKey);
            this.redisTemplate.delete(originKey);
            operations.set(newKey, data);
            if (expire != null) {
                operations.set(newKey, data, expire, TimeUnit.SECONDS);
            }
        }

    }

    private void updateUserAccessTokenAndRefreshToken(TenantType tenantType, String openId, String accessToken, String refreshToken) {
        String openIdColumn = TenantType.NEW == tenantType ? "new_user_open_id" : "old_user_open_id";
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq(openIdColumn, openId);
        List<UserInfo> userInfos = this.userRepository.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(userInfos)) {
            for(UserInfo userInfo : userInfos) {
                if (TenantType.NEW == tenantType) {
                    userInfo.setNewUserToken(accessToken);
                    userInfo.setNewUserRefreshToken(refreshToken);
                } else {
                    userInfo.setOldUserToken(accessToken);
                    userInfo.setOldUserRefreshToken(refreshToken);
                }

                this.userRepository.updateById(userInfo);
            }
        }

    }
}
