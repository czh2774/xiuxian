package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * User实体类，代表用户对象，映射到数据库中的users表。
 */
@Entity
@Table(name = "users")
public class User {

    // 用户主键，自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户的 OpenID，不能为空且唯一
    @Column(nullable = false, unique = true)
    private String openid;

    // 用户的 SessionKey，不能为空
    @Column(nullable = false)
    private String sessionKey;

    // 创建时间，默认当前时间
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 使用 @PrePersist 注解在保存之前自动设置创建时间
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    // Getter 和 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public LocalDateTime  getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime  createdAt) {
        this.createdAt = createdAt;
    }
}
