package org.com.gx.ebusiness.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 这个密钥很重要，相当于“印章”，以后生产环境要放到配置文件里
    private static final String SECRET_KEY = "MySuperSecretKeyForJwtSigning2025SpringBoot3";
    // Token 有效期设长一点，省得老让用户重新登录
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 365;

    // 生成一个 Token 字符串
    public String generateToken(String email) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // 从 Token 里面把邮箱取出来
    public String extractEmail(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 检查 Token 对不对、有没有过期
    public boolean isTokenValid(String token) {
        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}