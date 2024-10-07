package com.xiuxian.xiuxianserver.dto;

import java.util.logging.Logger;

/**
 * 通用 API 响应类，用于标准化服务端返回给客户端的数据。
 *
 * @param <T> 响应数据的类型
 */
public class CustomApiResponse<T> {
    private static final Logger LOGGER = Logger.getLogger(CustomApiResponse.class.getName());
    private int status;   // HTTP 状态码
    private String message; // 响应消息
    private T data;       // 响应数据

    // 无参构造方法
    public CustomApiResponse() {}

    // 构造方法：只有状态码
    public CustomApiResponse(int status) {
        setStatus(status);
    }

    // 构造方法：状态码和消息
    public CustomApiResponse(int status, String message) {
        setStatus(status);
        setMessage(message);
    }

    // 构造方法：状态码、消息和数据
    public CustomApiResponse(int status, String message, T data) {
        setStatus(status);
        setMessage(message);
        setData(data);
    }

    // 设置状态码，确保状态码有效，并记录日志
    public void setStatus(int status) {
        if (status < 100 || status > 599) {
            LOGGER.severe("尝试设置无效的 HTTP 状态码: " + status);
            throw new IllegalArgumentException("无效的 HTTP 状态码: " + status);
        }
        this.status = status;
    }

    // 获取状态码
    public int getStatus() {
        return status;
    }

    // 设置消息，确保消息不为空，并记录日志
    public void setMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            LOGGER.warning("尝试设置空的消息内容");
            throw new IllegalArgumentException("消息不能为 null 或空");
        }
        this.message = message;
    }

    // 获取消息
    public String getMessage() {
        return message;
    }

    // 设置数据
    public void setData(T data) {
        this.data = data;
    }

    // 获取数据
    public T getData() {
        return data;
    }

    // 重写 toString 方法以提供类内容的字符串表示
    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
