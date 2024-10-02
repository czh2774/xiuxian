package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "feature_unlock_condition")
public class FeatureUnlockCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long conditionId;

    private String conditionType; // 条件类型，例如建筑等级、科技等
    private Long targetId; // 目标ID，例如建筑ID、科技ID
    private int conditionValue; // 需要满足的条件值
    private int priority; // 优先级
    private String logic; // 逻辑操作符，例如 AND 或 OR
}
