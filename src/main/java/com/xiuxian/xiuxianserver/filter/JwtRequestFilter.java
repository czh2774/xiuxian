package com.xiuxian.xiuxianserver.filter;

import com.xiuxian.xiuxianserver.dto.UserDTO;
import com.xiuxian.xiuxianserver.util.JwtTokenUtil;
import com.xiuxian.xiuxianserver.common.CustomAuthenticationEntryPoint;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7).trim();
            logger.info("Extracted Token: {}", token);

            try {
                // 使用通用的 getClaim 方法提取用户信息
                Long platformUserId = jwtTokenUtil.getClaim(token, "platformUserId", Long.class);
                String username = jwtTokenUtil.getClaim(token, "userName", String.class);
                String authType = jwtTokenUtil.getClaim(token, "authType", String.class);

                // 使用 UserDTO 直接传递用户信息
                UserDTO userDTO = new UserDTO();
                userDTO.setPlatformUserId(platformUserId);
                userDTO.setName(username);
                userDTO.setLoginType(authType);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDTO, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authToken);


            } catch (Exception e) {
                logger.error("Token 验证失败: {}", e.getMessage());
                if (e instanceof ExpiredJwtException) {
                    request.setAttribute("JWT_EXPIRED", e);
                } else {
                    request.setAttribute("JWT_INVALID", e);
                }
                customAuthEntryPoint.commence(request, response, new org.springframework.security.core.AuthenticationException("JWT 验证失败", e) {});
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
