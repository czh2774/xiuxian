package com.xiuxian.xiuxianserver.common;

import com.xiuxian.xiuxianserver.dto.CustomApiResponse;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器，捕获项目中的异常并进行统一处理。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有的通用异常。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomApiResponse<Object> handleGenericException(Exception ex) {
        logger.error("系统内部错误：", ex);
        return new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null);
    }

    /**
     * 处理自定义的资源未找到异常。
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomApiResponse<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.warn("资源未找到：{}", ex.getMessage());
        return new CustomApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
    }

    /**
     * 处理 404 错误（找不到资源）。
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomApiResponse<Object> handleNotFoundException(NoHandlerFoundException ex) {
        logger.warn("资源未找到：", ex);
        return new CustomApiResponse<>(HttpStatus.NOT_FOUND.value(), "Page not found", null);
    }
}
