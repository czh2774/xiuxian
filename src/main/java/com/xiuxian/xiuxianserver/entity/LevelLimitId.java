package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.LimitType;
import lombok.Data;
import java.io.Serializable;

@Data
public class LevelLimitId implements Serializable {
    private Integer level;
    private LimitType limitType;
} 