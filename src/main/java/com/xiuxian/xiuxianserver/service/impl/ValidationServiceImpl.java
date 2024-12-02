package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.ValidationCondition;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final CharacterProfileService characterProfileService;

    @Override
    public boolean validateResource(Long characterId, String resourceType, int requiredAmount) {
        return characterProfileService.hasSufficientResource(characterId, resourceType, requiredAmount);
    }

    @Override
    public boolean validateConditions(Long characterId, ValidationCondition conditions) {
        // 校验资源
        if (conditions.getResources() != null) {
            for (Map.Entry<String, Integer> resource : conditions.getResources().entrySet()) {
                if (!validateResource(characterId, resource.getKey(), resource.getValue())) {
                    return false;
                }
            }
        }

        // 未来可以扩展道具、属性等校验逻辑

        return true;
    }
}
