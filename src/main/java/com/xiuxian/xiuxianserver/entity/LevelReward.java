package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import com.xiuxian.xiuxianserver.enums.RewardType;

@Data
@Entity
public class LevelReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer level;
    @Enumerated(EnumType.STRING)
    private RewardType rewardType;
    private Long rewardId;
    private Integer amount;
} 