package com.xiuxian.xiuxianserver.dto;

/**
 * IdRequestDTO类，用于传输只包含ID的请求数据（例如获取、删除操作）。
 */
public class IdRequestDTO {
    private Long id;  // 请求的ID

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
