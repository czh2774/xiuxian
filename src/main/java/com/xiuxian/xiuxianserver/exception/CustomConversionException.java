package com.xiuxian.xiuxianserver.exception;

/**
 * 自定义转换异常，用于在 DTO 和实体之间转换时捕获转换错误。
 */
public class CustomConversionException extends RuntimeException {
    public CustomConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
