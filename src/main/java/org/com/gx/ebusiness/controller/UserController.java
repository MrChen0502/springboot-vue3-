package org.com.gx.ebusiness.controller;

import org.com.gx.ebusiness.common.Result;
import org.com.gx.ebusiness.entity.BUser;
import org.com.gx.ebusiness.service.UserService;
import org.com.gx.ebusiness.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<String> register(@RequestBody BUser bUser) {
        boolean isSuccess = userService.register(bUser);
        if (isSuccess) {
            return Result.success("注册成功，请登录");
        } else {
            return Result.error("邮箱已被注册");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody BUser bUser) {
        BUser user = userService.login(bUser.getBemail(), bUser.getBpwd());

        if (user != null) {
            // 登录成功，不返回用户信息，返回一个 Token 给前端
            String token = jwtUtil.generateToken(user.getBemail());
            return Result.success(token);
        } else {
            return Result.error("邮箱或密码错误");
        }
    }
}