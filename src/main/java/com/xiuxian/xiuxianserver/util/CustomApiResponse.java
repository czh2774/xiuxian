package com.xiuxian.xiuxianserver.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 通用 API 响应类，用于标准化服务端返回给客户端的数据
 *
 * @param <T> 响应数据的类型
 */
@Data  // 自动生成 getter/setter、toString、equals、hashCode 等方法
@NoArgsConstructor  // 生成无参构造器
@AllArgsConstructor // 生成全参构造器
public class CustomApiResponse<T> {

    private LocalDateTime timestamp = LocalDateTime.now(); // 自动生成时间戳
    private int status;   // HTTP 状态码描述
    private String message;  // 响应消息
    private T data;          // 响应数据
    private String path;     // 请求的路径

    // 手动构造函数：允许传入 HttpStatus 作为 status 参数，并生成时间戳
    public CustomApiResponse(int status, String message, T data, String path) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.path = path;
        this.timestamp = LocalDateTime.now(); // 设置当前时间戳
    }

    // 静态方法，用于生成成功响应
    public static <T> CustomApiResponse<T> success(String message, T data, String path) {
        return new CustomApiResponse<>(HttpStatus.OK.value(), message, data, path);
    }

    // 静态方法，用于生成错误响应
    public static <T> CustomApiResponse<T> error(int status, String message, String path) {
        return new CustomApiResponse<>(status, message, null, path);
    }
}
