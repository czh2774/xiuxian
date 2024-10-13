package com.xiuxian.xiuxianserver.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 * 全局异常处理类，捕获并处理系统中的异常，返回友好的错误消息。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理资源未找到异常
     *
     * @param ex 捕获到的 ResourceNotFoundException
     * @return 友好的错误响应
     */
    @ExceptionHandler(ResourceNotFoundException.class)  // 修正为 ResourceNotFoundException
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * 处理非法参数异常
     *
     * @param ex 捕获到的 IllegalArgumentException
     * @return 友好的错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理校验失败的异常
     * 捕获 MethodArgumentNotValidException 并返回所有详细的字段校验错误信息
     *
     * @param ex 捕获到的 MethodArgumentNotValidException 异常对象
     *           该异常由 Spring 在验证失败时抛出，包含详细的字段错误信息
     * @return 包含详细错误信息的 ResponseEntity，以及 HTTP 400 BAD_REQUEST 状态码
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理其他未捕获的异常
     *
     * @param ex 捕获到的异常
     * @return 友好的错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
