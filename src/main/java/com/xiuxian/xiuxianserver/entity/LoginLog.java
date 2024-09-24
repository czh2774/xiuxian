package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * LoginLog实体类，代表用户的登录日志，映射到数据库中的login_logs表。
 * 注意：这里不使用外键来关联用户表，而是使用userId字段存储用户ID。
 */
@Entity
@Table(name = "login_logs")
public class LoginLog {

    // 日志主键，自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户ID，不使用外键
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 登录时间，默认当前时间
    @Column(name = "login_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime loginTime;

    // 登录IP地址
    @Column(name = "ip_address")
    private String ipAddress;

    // Getter 和 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
