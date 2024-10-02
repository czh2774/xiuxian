package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.*;


/**
 * 玩家信息表，存储所有玩家基础信息和登录信息。
 * 支持多平台（如微信、独立APP）的登录和账号管理。
 */
@Data
@Entity
@Table(name = "player_profile")
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long playerId; // 全局唯一玩家ID，雪花ID生成

    @Column(nullable = false, length = 64)
    private String platformUserId; // 平台用户唯一标识符（如微信的openid）

    @Column(length = 64)
    private String platformGlobalId; // 跨平台唯一标识符（如微信的unionid）

    @Column(length = 128)
    private String platformSessionKey; // 平台会话密钥，用于加密解密授权信息

    @Column(length = 20)
    private String authStatus; // 用户授权状态（如是否完成授权）

    @Column(length = 255)
    private String authScope; // 用户授权范围（如公开信息、手机号等）

    @Column(nullable = false, length = 255)
    private String name; // 玩家名称，服务器内唯一

    @Column(nullable = false, length = 20)
    private String loginType; // 登录方式，如微信（WECHAT）、邮箱（EMAIL）、手机号（PHONE）

    @Column(nullable = false)
    private Long serverId; // 当前所属服务器ID

    @Column(columnDefinition = "JSON")
    private String serverHistory; // 玩家曾经所在的服务器历史（JSON格式存储）

    @Column(length = 255)
    private String jwtToken; // 用户的JWT token，用于用户身份鉴权

    @Column
    private String lastLoginTime; // 最近登录时间

    @Column(length = 45)
    private String ipAddress; // 用户的登录IP地址

    @Column(columnDefinition = "JSON")
    private String accountBinding; // 多平台账号绑定信息（如绑定邮箱、微信、手机号等）

    @Column(length = 20)
    private String bindingStatus; // 绑定状态（如已绑定、未绑定）

    @Column(columnDefinition = "JSON")
    private String backupData; // 用户数据备份字段（用于数据恢复）

    @Column(length = 128, nullable = false)
    private String identifier; // 由 loginType 和 platformUserId 组合生成的唯一标识符，用于快速查找用户

    // Constructor, Getters, Setters will be handled by Lombok @Data
}
