package com.lark.service.impl;

import com.lark.api.contact.user.UserApi;
import com.lark.data.bean.Response;
import com.lark.data.constants.UserIdTypeEnum;
import com.lark.data.dto.contact.UserDTO;
import com.lark.exception.CustomException;
import com.lark.service.UserService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserApi userApi;

    public UserServiceImpl() {
    }

    public UserDTO getUserById(UserIdTypeEnum userIdType, String userAccessToken, String userId) {
        Response<Map<String, UserDTO>> data = this.userApi.queryUser(userAccessToken, userIdType, userId);
        UserDTO user = null;
        if (data != null && data.getData() != null && (user = (UserDTO)((Map)data.getData()).get("user")) != null) {
            return user;
        } else {
            throw new CustomException("用户信息获取有误");
        }
    }
}
