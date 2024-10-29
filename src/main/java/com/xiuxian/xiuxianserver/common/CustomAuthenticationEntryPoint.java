package com.xiuxian.xiuxianserver.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String errorMessage = "Unauthorized";

        // 检查是否存在特定的 JWT 错误
        if (request.getAttribute("JWT_EXPIRED") != null) {
            errorMessage = "JWT Token 已过期";
        } else if (request.getAttribute("JWT_INVALID") != null) {
            errorMessage = "JWT Token 无效";
        }

        CustomApiResponse<Object> CustomApiResponse = new CustomApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                errorMessage,
                null,
                request.getRequestURI()
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 注册 Java 8 时间序列化模块
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());  // 注册时间模块
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // 禁止时间戳写入

        response.getWriter().write(mapper.writeValueAsString(CustomApiResponse));
    }

}
