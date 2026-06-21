package org.com.gx.ebusiness.service;

import org.com.gx.ebusiness.entity.BUser;

public interface UserService {


    /**
     * 用户注册
     * @param bUser 前端传来的用户信息（包含邮箱和明文密码）
     * @return true 表示注册成功，false 表示注册失败（如邮箱已存在）
     */
    boolean register(BUser bUser);

    /**
     * 用户登录
     * @param bemail 邮箱
     * @param bpwd 前端传来的明文密码
     * @return 登录成功返回用户对象，失败返回 null
     */
    BUser login(String bemail, String bpwd);
}
