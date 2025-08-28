package com.lark.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lark.data.constants.TenantType;
import com.lark.data.pojo.UserInfo;
import java.util.List;

public interface UserInfoService {
    UserInfo queryUserInfoByOpenId(String openId);

    UserInfo queryUserInfoByOpenId(TenantType tenantType, String openId);

    String queryUserAccessTokenByOpenId(TenantType tenantType, String openId);

    String queryUserRefreshTokenByOpenId(TenantType tenantType, String openId);

    List<UserInfo> queryUserInfoByOldUserOpenId(String oldUserOpenId);

    List<UserInfo> queryUsers(QueryWrapper<UserInfo> queryWrapper);

    UserInfo queryUserInfoByOpenId(String oldUserOpenId, String newUserOpenId);

    int updateUserInfo(UserInfo userInfo);

    int insertUserInfo(UserInfo userInfo);

    void replaceUserOpenId(String oldUserOpenId, String newUserOpenId, String replaceNewUserOpenId);
}
