package com.xiuxian.xiuxianserver.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class ResponseUtils {

    public static <T> ResponseEntity<ApiResponse<T>> buildResponse(
            String status, String message, T data, HttpStatus httpStatus, HttpServletRequest request) {
        ApiResponse<T> response = new ApiResponse<>(
                status, message, data, request.getRequestURI()
        );
        return new ResponseEntity<>(response, httpStatus);
    }
}
