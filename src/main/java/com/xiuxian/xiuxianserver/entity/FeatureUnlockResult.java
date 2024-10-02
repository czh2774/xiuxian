package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "feature_unlock_result")
public class FeatureUnlockResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;

    private Long featureId; // 功能ID
    private String featureType; // 功能类型，例如建筑、科技、UI
    private Long conditionId; // 关联的条件ID
}
