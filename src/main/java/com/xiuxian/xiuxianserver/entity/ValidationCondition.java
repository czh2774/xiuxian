package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.Embeddable;
import java.util.Map;

@Data
@Embeddable
public class ValidationCondition {
    private int playerLevel;
    private String[] requiredBuildings;
    private String[] requiredItems;
    private Map<String, Integer> resources;
} 