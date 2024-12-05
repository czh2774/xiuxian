package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.Embeddable;
import java.util.Map;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;

@Data
@Embeddable
public class ValidationCondition {
    private int playerLevel;
    private String[] requiredBuildings;
    private String[] requiredItems;
    private Map<String, Integer> resources;
    private Map<AccelerateItemType, Integer> items;
} 