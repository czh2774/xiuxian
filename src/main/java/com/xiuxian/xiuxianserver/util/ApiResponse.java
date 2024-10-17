package com.xiuxian.xiuxianserver.util;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private String status;
    private String message;
    private T data;
    private String path;

    public ApiResponse(String status, String message, T data, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
        this.path = path;
    }

    // Getters and setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getPath() {
        return path;
    }
}
