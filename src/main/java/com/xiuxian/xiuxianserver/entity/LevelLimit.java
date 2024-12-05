package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import com.xiuxian.xiuxianserver.enums.LimitType;

@Data
@Entity
@IdClass(LevelLimitId.class)
public class LevelLimit {
    @Id
    private Integer level;
    @Id
    @Enumerated(EnumType.STRING)
    private LimitType limitType;
    private Integer limitValue;
} 