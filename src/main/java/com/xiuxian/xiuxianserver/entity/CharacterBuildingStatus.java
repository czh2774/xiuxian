package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * CharacterBuildingStatus 实体类
 * 用于存储角色在特定位置的建筑状态信息
 */
@Data
@NoArgsConstructor  // 无参构造函数
@AllArgsConstructor // 全参构造函数
@Entity
@Builder
@Table(name = "character_building_status")
@Schema(description = "角色建筑状态实体类，存储角色在特定位置的建筑及其状态信息")
public class CharacterBuildingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @Schema(description = "状态的唯一标识符", example = "UUID格式")
    private String statusId; // 状态唯一ID (UUID)

    @Column(nullable = false)
    @Schema(description = "角色的唯一标识符", example = "UUID格式")
    private String characterId; // 角色ID

    @Column(nullable = false)
    @Schema(description = "建筑的唯一标识符", example = "UUID格式")
    private String buildingId; // 建筑ID

    @Column(nullable = false)
    @Schema(description = "建筑所在位置的唯一标识符", example = "UUID格式")
    private String locationId; // 建筑位置ID

    @Column(nullable = false)
    @Schema(description = "建筑的当前等级", example = "1")
    private int buildingLevel; // 建筑等级

    @Schema(description = "建造开始时间")
    private LocalDateTime buildStartTime; // 建造开始时间

    @Schema(description = "建造结束时间")
    private LocalDateTime buildEndTime; // 建造结束时间

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "建筑的状态", example = "under_construction")
    private BuildingStatus status; // 建筑状态

    @Column(nullable = false, updatable = false)
    @Schema(description = "状态创建时间")
    private LocalDateTime createdAt; // 记录创建时间

    @Column(nullable = false)
    @Schema(description = "状态最后更新时间")
    private LocalDateTime updatedAt; // 记录更新时间

    // 建筑状态枚举类型
    public enum BuildingStatus {
        UNDER_CONSTRUCTION,  // 正在建造
        COMPLETED,           // 建造完成
        UPGRADING            // 正在升级
    }
}
