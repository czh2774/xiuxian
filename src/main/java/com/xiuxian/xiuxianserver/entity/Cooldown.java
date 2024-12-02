package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 冷却时间管理实体类
 */
@Data
@Entity
@Table(name = "cooldown_management")
public class Cooldown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long characterId; // 角色ID

    @Column(nullable = false)
    private String type; // CD类型（建筑升级、科技研究、装备突破）

    @Column(nullable = false)
    private Long targetId; // 目标ID（如建筑ID、科技ID、装备ID）

    @Column(nullable = false)
    private Integer queueId; // 队列ID，支持多队列

    @Column(nullable = false)
    private LocalDateTime startTime; // 冷却开始时间

    @Column(nullable = false)
    private LocalDateTime endTime; // 冷却结束时间

    @Column(nullable = false)
    private Boolean isCompleted = false; // 是否完成
}
