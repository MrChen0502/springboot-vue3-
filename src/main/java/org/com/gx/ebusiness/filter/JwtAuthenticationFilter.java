package org.com.gx.ebusiness.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.com.gx.ebusiness.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("================ JwtAuthenticationFilter 被触发了！ ================");
        String authHeader = request.getHeader("Authorization");
        System.out.println("当前收到的 Authorization 请求头: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // 验证 Token
            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                System.out.println("Token 有效，解析出邮箱: " + email); // 加个打印

                // 💡 关键：将用户身份存入 SecurityContext！
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Token 无效！");
            }
        } else {
            System.out.println("未检测到 Authorization 请求头");
        }

        filterChain.doFilter(request, response);
    }
}