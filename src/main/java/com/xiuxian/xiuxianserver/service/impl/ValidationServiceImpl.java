package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.ValidationCondition;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import com.xiuxian.xiuxianserver.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final CharacterProfileService characterProfileService;
    private final CharacterItemService itemService;

    @Override
    public boolean validateResource(Long characterId, String resourceType, int requiredAmount) {
        return characterProfileService.hasSufficientResource(characterId, resourceType, requiredAmount);
    }

    @Override
    public boolean validateItem(Long characterId, AccelerateItemType itemType, int requiredCount) {
        return itemService.hasSufficientItems(characterId, itemType, requiredCount);
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

        // 校验道具
        if (conditions.getItems() != null) {
            for (Map.Entry<AccelerateItemType, Integer> item : conditions.getItems().entrySet()) {
                if (!validateItem(characterId, item.getKey(), item.getValue())) {
                    return false;
                }
            }
        }

        return true;
    }
}
