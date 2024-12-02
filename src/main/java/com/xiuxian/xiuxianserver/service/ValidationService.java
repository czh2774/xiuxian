package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.ValidationCondition;

public interface ValidationService {
    boolean validateResource(Long characterId, String resourceType, int requiredAmount);
    boolean validateConditions(Long characterId, ValidationCondition conditions);
}
