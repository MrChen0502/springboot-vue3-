package org.com.gx.ebusiness.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 密钥（生产环境必须放在配置文件或环境变量中，这里演示用固定字符串）
    private static final String SECRET_KEY = "MySuperSecretKeyForJwtSigning2025SpringBoot3";
    // 过期时间（7天，单位毫秒）
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 365;

    // 生成 Token
    public String generateToken(String email) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                .subject(email) // 把邮箱作为 Token 的内容
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // 从 Token 中解析出邮箱
    public String extractEmail(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 校验 Token 是否有效（未过期）
    public boolean isTokenValid(String token) {
        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}