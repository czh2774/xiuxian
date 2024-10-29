package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * ConstructionQueue 实体类
 * 用于存储角色的建造队列信息
 */
@Data
@NoArgsConstructor  // 无参构造函数
@AllArgsConstructor // 全参构造函数
@Entity
@Builder
@Table(name = "construction_queue")
@Schema(description = "建造队列实体类，存储角色的建造队列信息")
public class ConstructionQueue {

    @Id
    @Column(nullable = false, updatable = false)
    @Schema(description = "队列的唯一标识符", example = "雪花ID")
    private long id; // 队列唯一ID (UUID)

    @Column(nullable = false)
    @Schema(description = "角色的唯一标识符", example = "UUID格式")
    private long characterId; // 角色ID (关联角色表)

    @Column(nullable = false)
    @Schema(description = "建筑的唯一标识符", example = "UUID格式")
    private long buildingId; // 建筑ID (关联建筑表)

    @Column(nullable = false)
    @Schema(description = "建造队列的索引值（1 或 2）", example = "1")
    private int queueIndex; // 队列索引（1或2）

    @Column(nullable = false)
    @Schema(description = "建造开始时间")
    private LocalDateTime startTime; // 建造开始时间

    @Column(nullable = false)
    @Schema(description = "建造结束时间")
    private LocalDateTime endTime; // 建造结束时间

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "建造队列状态", example = "pending")
    private QueueStatus status; // 队列状态

    // 枚举类型，用于定义队列状态
    public enum QueueStatus {
        PENDING,
        BUILDING,
        COMPLETED
    }
}
