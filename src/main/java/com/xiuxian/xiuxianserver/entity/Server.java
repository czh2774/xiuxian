package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.entity.ServerStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 服务器实体类，包含服务器的基本信息。
 */
@Entity
public class Server {

    /**
     * 服务器的唯一标识ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 服务器的名称
     */
    private String name;

    /**
     * 服务器的描述信息
     */
    private String description;

    /**
     * 服务器的状态（ACTIVE：活跃，INACTIVE：不活跃，MAINTENANCE：维护中）
     */
    @Enumerated(EnumType.STRING)
    private ServerStatus status;

    /**
     * 当前服务器上的玩家数量
     */
    private int playerCount;

    /**
     * 服务器最大支持的玩家数量
     */
    private int maxPlayers;

    /**
     * 服务器的IP地址
     */
    private String ipAddress;

    /**
     * 服务器的创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 初始化服务器创建时间为当前时间
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
