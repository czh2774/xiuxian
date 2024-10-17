package com.xiuxian.xiuxianserver.exception;

import com.xiuxian.xiuxianserver.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ApiResponse<Object>> buildResponse(
            String status, String message, HttpStatus httpStatus, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(
                status, message, null, request.getRequestURI()
        );
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        logger.error("资源未找到: {}", ex.getMessage());
        return buildResponse("NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateResource(
            DuplicateResourceException ex, HttpServletRequest request) {
        logger.error("资源重复: {}", ex.getMessage());
        return buildResponse("CONFLICT", ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {
        logger.warn("认证失败: {}", ex.getMessage());
        ResponseEntity<ApiResponse<Object>> response = buildResponse("UNAUTHORIZED", ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
        logger.info("返回的响应: {}", response);  // 记录构造的 ResponseEntity
        return response;
    }


    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthorizationException(
            AuthorizationException ex, HttpServletRequest request) {
        logger.error("权限不足: {}", ex.getMessage());
        return buildResponse("FORBIDDEN", ex.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(CooldownException.class)
    public ResponseEntity<ApiResponse<Object>> handleCooldownException(
            CooldownException ex, HttpServletRequest request) {
        logger.warn("冷却时间未结束: {}", ex.getMessage());
        return buildResponse("TOO_MANY_REQUESTS", ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS, request);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidParameterException(
            InvalidParameterException ex, HttpServletRequest request) {
        logger.warn("无效参数: {}", ex.getMessage());
        return buildResponse("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(
            Exception ex, HttpServletRequest request) {
        logger.error("服务器内部错误: {}", ex.getMessage(), ex);
        return buildResponse("INTERNAL_SERVER_ERROR", "服务器发生了未知错误，请稍后重试", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}
