package org.com.gx.ebusiness.service.impl;

import org.com.gx.ebusiness.entity.BUser;
import org.com.gx.ebusiness.mapper.UserMapper;
import org.com.gx.ebusiness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;// 注入 Spring Security 的加密器

    @Override
    public boolean register(BUser bUser){
        // 1. 先检查邮箱是否已存在（生产环境必须做，防止重复注册）
        BUser existUser = userMapper.findByEmail(bUser.getBemail());
        if (existUser != null) {
            return false; // 邮箱已被注册
        }

        // 2. 加密密码（2025标准：BCrypt）
        String encodedPassword = passwordEncoder.encode(bUser.getBpwd());
        bUser.setBpwd(encodedPassword);

        // 3. 存入数据库
        int rows = userMapper.insertUser(bUser);
        return rows > 0;
    }

    @Override
    public BUser login(String bemail, String bpwd) {
        // 1. 根据邮箱查用户
        BUser user = userMapper.findByEmail(bemail);

        // 2. 用户不存在
        if (user == null) {
            return null;
        }

        // 3. 密码比对
        if (passwordEncoder.matches(bpwd, user.getBpwd())) {
            user.setBpwd(null); // 安全：返回前清空密码
            return user;
        }

        // 4. 密码错误
        return null;
    }
}
