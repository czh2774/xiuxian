package com.xiuxian.xiuxianserver.exception;

import com.xiuxian.xiuxianserver.util.CustomApiResponse;
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

    /**
     * 构建统一的 API 响应结构，使用 HttpStatus 枚举。
     */
    private ResponseEntity<CustomApiResponse<Object>> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        CustomApiResponse<Object> response = new CustomApiResponse<>(
                status.value(), message, null, request.getRequestURI()
        );
        return new ResponseEntity<>(response, status);
    }

    /**
     * 资源未找到异常处理
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.error("资源未找到: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * 处理重复资源异常
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        logger.error("资源重复: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        logger.warn("认证失败: {}", ex.getMessage());
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    /**
     * 处理权限不足异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleAuthorizationException(AuthorizationException ex, HttpServletRequest request) {
        logger.error("权限不足: {}", ex.getMessage());
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    /**
     * 处理冷却时间异常
     */
    @ExceptionHandler(CooldownException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleCooldownException(CooldownException ex, HttpServletRequest request) {
        logger.warn("冷却时间未结束: {}", ex.getMessage());
        return buildResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage(), request);
    }

    /**
     * 处理参数无效异常
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleInvalidParameterException(InvalidParameterException ex, HttpServletRequest request) {
        logger.warn("无效参数: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<Object>> handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error("服务器内部错误: {}", ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "服务器发生了未知错误，请稍后重试", request);
    }

    /**
     * 处理自定义转换异常
     */
    @ExceptionHandler(CustomConversionException.class)
    public ResponseEntity<CustomApiResponse<Object>> handleCustomConversionException(CustomConversionException ex, HttpServletRequest request) {
        logger.error("数据转换错误: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "数据转换错误，请检查输入数据的格式或内容", request);
    }

}
