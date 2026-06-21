package org.com.gx.ebusiness.entity;

import lombok.Data;

@Data
public class BUser {
    // 对应数据库字段：id (主键)
    private Integer id;

    // 对应数据库字段：bemail (邮箱/账号)
    // 加上 @NotBlank 是做参数校验用的（后端拦截空邮箱）
    // 依赖项里的 Validation 就是为这个准备的
    private String bemail;

    // 对应数据库字段：bpwd (密码)
    // 注意：数据库中存的是加密后的 BCrypt 密文，不是明文
    private String bpwd;
}
