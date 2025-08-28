package com.lark.service.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lark.data.pojo.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseMapper<UserInfo> {
}
