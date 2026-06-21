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

        System.out.println("===== 过滤器拦截到了请求 =====");
        String authHeader = request.getHeader("Authorization");
        System.out.println("请求头里的 Authorization: " + authHeader);

        // 如果请求头里有 Bearer 开头的东西，说明带了 Token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // 把 "Bearer " 去掉，只剩 Token 本身

            // 检查 Token 是不是伪造的或者过期的
            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                System.out.println("Token 校验通过，用户邮箱是: " + email);

                // 把用户身份塞给 Spring Security，告诉它这个人已经登录了
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Token 无效！");
            }
        } else {
            System.out.println("没带 Authorization 头，或者格式不对");
        }

        // 放行，让请求继续往后走
        filterChain.doFilter(request, response);
    }
}