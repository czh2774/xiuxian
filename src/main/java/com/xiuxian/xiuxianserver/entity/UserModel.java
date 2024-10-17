package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_model")
@Schema(description = "用户实体类，存储用户的基本信息、支付信息和状态")
public class UserModel {

    @Id
    @Column(nullable = false, updatable = false)
    @Schema(description = "全局唯一玩家ID")
    @Comment("全局唯一玩家ID")
    private Long playerId;  // 全局唯一玩家ID

    @Column(nullable = false, length = 64)
    @Schema(description = "平台用户唯一标识符（如微信的openid）")
    @Comment("平台用户唯一标识符（如微信的openid）")
    private Long platformUserId;  // 平台用户唯一标识符

    @Column(length = 64)
    @Schema(description = "跨平台唯一标识符（如微信的unionId）")
    @Comment("跨平台唯一标识符（如微信的unionId）")
    private String platformGlobalId;  // 跨平台唯一标识符

    @Column(length = 128)
    @Schema(description = "平台会话密钥，用于加密解密授权信息")
    @Comment("平台会话密钥，用于加密解密授权信息")
    private String platformSessionKey;  // 平台会话密钥

    @Column(length = 20)
    @Schema(description = "用户授权状态")
    @Comment("用户授权状态")
    private String authStatus;  // 用户授权状态

    @Column(length = 255)
    @Schema(description = "用户授权范围")
    @Comment("用户授权范围")
    private String authScope;  // 用户授权范围

    @Column(nullable = false, length = 255)
    @Schema(description = "玩家名称，服务器内唯一")
    @Comment("玩家名称，服务器内唯一")
    private String name;  // 玩家名称

    @Column(nullable = false, length = 20)
    @Schema(description = "登录方式，如微信（WECHAT）、邮箱（EMAIL）、手机号（PHONE）")
    @Comment("登录方式，如微信（WECHAT）、邮箱（EMAIL）、手机号（PHONE）")
    private String loginType;  // 登录方式

    @Column(length = 512)
    @Schema(description = "用户的JWT token，用于用户身份鉴权")
    @Comment("用户的JWT token，用于用户身份鉴权")
    private String jwtToken;  // 用户的JWT token

    @Column
    @Schema(description = "最近登录时间")
    @Comment("最近登录时间")
    private String lastLoginTime;  // 最近登录时间

    @Column(length = 45)
    @Schema(description = "用户的登录IP地址")
    @Comment("用户的登录IP地址")
    private String ipAddress;  // 用户的登录IP地址

    @Column(columnDefinition = "JSON")
    @Schema(description = "多平台账号绑定信息（JSON格式存储）")
    @Comment("多平台账号绑定信息（JSON格式存储）")
    private String accountBinding;  // 多平台账号绑定信息

    @Column(length = 20)
    @Schema(description = "绑定状态")
    @Comment("绑定状态")
    private String bindingStatus;  // 绑定状态

    @Column(columnDefinition = "JSON")
    @Schema(description = "用户数据备份字段（用于数据恢复）")
    @Comment("用户数据备份字段（用于数据恢复）")
    private String backupData;  // 用户数据备份字段

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "用户账户的创建时间")
    @Comment("用户账户的创建时间")
    private LocalDateTime createdAt;  // 用户创建时间

    @UpdateTimestamp
    @Column(nullable = false)
    @Schema(description = "用户最后登录时间")
    @Comment("用户最后登录时间")
    private LocalDateTime lastLogin;  // 用户最后登录时间

    @Column(nullable = false)
    @Schema(description = "用户账户是否启用")
    @Comment("用户账户是否启用")
    private boolean enabled;  // 用户是否启用

    @Column(nullable = false)
    @Schema(description = "用户账户是否被封禁")
    @Comment("用户账户是否被封禁")
    private boolean banned;  // 用户是否被封禁

    @Column(nullable = true)
    @Schema(description = "用户的总充值金额")
    @Comment("用户的总充值金额")
    private Double totalSpent;  // 用户总充值金额

    @Column(nullable = true)
    @Schema(description = "用户的VIP等级")
    @Comment("用户的VIP等级")
    private Integer vipLevel;  // 用户VIP等级

    @Column(length = 255)
    @Schema(description = "用户头像URL")
    @Comment("用户头像URL")
    private String avatarUrl;  // 用户头像URL

    @Column(length = 500)
    @Schema(description = "用户的个性化设置（JSON格式存储）")
    @Comment("用户的个性化设置（JSON格式存储）")
    private String preferences;  // 用户个性化设置

    @Column(length = 255)
    @Schema(description = "外部平台ID（未来多平台支持）")
    @Comment("外部平台ID（未来多平台支持）")
    private String externalId;  // 外部平台的ID



}
