package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.ValidationCondition;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;

public interface ValidationService {
    boolean validateResource(Long characterId, String resourceType, int requiredAmount);
    boolean validateItem(Long characterId, AccelerateItemType itemType, int requiredCount);
    boolean validateConditions(Long characterId, ValidationCondition conditions);
}
