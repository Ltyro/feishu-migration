package com.lark.service;

import com.lark.data.constants.UserIdTypeEnum;
import com.lark.data.dto.contact.UserDTO;

public interface UserService {
    UserDTO getUserById(UserIdTypeEnum userIdType, String userAccessToken, String userId);
}
