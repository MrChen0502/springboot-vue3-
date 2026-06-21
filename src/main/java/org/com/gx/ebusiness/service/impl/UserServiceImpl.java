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
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(BUser bUser) {
        // 先看看这个邮箱有没有注册过
        BUser existUser = userMapper.findByEmail(bUser.getBemail());
        if (existUser != null) {
            return false; // 已经被注册了
        }

        // 把明文密码加密一下，再存库
        String encodedPassword = passwordEncoder.encode(bUser.getBpwd());
        bUser.setBpwd(encodedPassword);

        int rows = userMapper.insertUser(bUser);
        return rows > 0;
    }

    @Override
    public BUser login(String bemail, String bpwd) {
        BUser user = userMapper.findByEmail(bemail);

        if (user == null) {
            return null;
        }

        // 拿着前端传来的明文密码，和库里存的那个加密密码比一下
        if (passwordEncoder.matches(bpwd, user.getBpwd())) {
            user.setBpwd(null); // 返回的时候别把密码带出去，不安全
            return user;
        }

        return null;
    }
}