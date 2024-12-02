package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 冷却时间管理的 DTO（数据传输对象）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CooldownDTO {

    private Long id; // 冷却记录的唯一标识
    private Long characterId; // 角色ID
    private String type; // CD类型（建筑升级、科技研究、装备突破）
    private Long targetId; // 目标ID（如建筑ID、科技ID、装备ID）
    private Integer queueId; // 队列ID，支持多队列
    private LocalDateTime startTime; // 冷却开始时间
    private LocalDateTime endTime; // 冷却结束时间
    private boolean isCompleted; // 冷却是否完成

    /**
     * 基于 Cooldown 实体的构造方法
     *
     * @param cooldown 冷却实体对象
     */
    public CooldownDTO(Cooldown cooldown) {
        this.id = cooldown.getId();
        this.characterId = cooldown.getCharacterId();
        this.type = cooldown.getType();
        this.targetId = cooldown.getTargetId();
        this.queueId = cooldown.getQueueId();
        this.startTime = cooldown.getStartTime();
        this.endTime = cooldown.getEndTime();
        this.isCompleted = cooldown.getIsCompleted();
    }
}
