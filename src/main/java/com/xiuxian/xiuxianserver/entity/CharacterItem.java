package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.ItemCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * CharacterItem 实体类
 * 用于记录角色拥有的道具及其状态信息
 */
@Data
@NoArgsConstructor  // 无参构造函数
@AllArgsConstructor // 全参构造函数
@Entity
@Builder
@Table(name = "character_items")
@Schema(description = "玩家道具实体类，存储玩家拥有的道具及其相关信息")
public class CharacterItem {

    @Id
    @Column(nullable = false, updatable = false)
    @Schema(description = "道具实例的唯一标识符", example = "雪花ID")
    private Long id; // 道具实例ID

    @Column(nullable = false)
    @Schema(description = "角色的唯一标识符", example = "UUID格式")
    private Long characterId; // 角色ID

    @Column(nullable = false)
    @Schema(description = "道具模板的唯一标识符", example = "UUID格式")
    private Long itemTemplateId; // 道具模板ID

    @Column(nullable = false)
    @Schema(description = "道具数量", example = "1")
    private int quantity; // 道具数量

    @Column(nullable = false)
    @Schema(description = "道具获取时间")
    private LocalDateTime acquiredAt; // 获取时间

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "道具的背包标签类型", example = "ITEM")
    private ItemCategory itemCategory; // 道具类别

    @Schema(description = "道具最后一次使用时间")
    private LocalDateTime lastUsedAt; // 最后一次使用时间

    @Column(nullable = false)
    @Schema(description = "道具是否已装备", example = "false")
    private boolean isEquipped; // 是否装备

    @Column(nullable = false, updatable = false)
    @Schema(description = "道具创建时间")
    private LocalDateTime createdAt; // 记录创建时间

    @Column(nullable = false)
    @Schema(description = "道具最后一次更新时间")
    private LocalDateTime updatedAt; // 记录更新时间

    /**
     * 在保存之前自动设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 在更新之前自动设置更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
