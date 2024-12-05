package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LevelTemplate {
    @Id
    private Integer level;
    private String description;
    private Integer requiredExp;
} 