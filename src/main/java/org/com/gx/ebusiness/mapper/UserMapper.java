package org.com.gx.ebusiness.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.com.gx.ebusiness.entity.BUser;

@Mapper
public interface UserMapper {

    // 生产环境：只根据邮箱查人，密码比对交给 Spring Security
    BUser findByEmail(@Param("bemail") String bemail);

    // 插入新用户
    int insertUser(BUser bUser);
}

