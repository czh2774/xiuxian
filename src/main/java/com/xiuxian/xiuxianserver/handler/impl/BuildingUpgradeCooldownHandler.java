package com.xiuxian.xiuxianserver.handler.impl;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.handler.CooldownCompletionHandler;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingUpgradeCooldownHandler implements CooldownCompletionHandler {
    private final CharacterBuildingService buildingService;
    
    @Override
    public void onCooldownComplete(Cooldown cooldown) {
        buildingService.completeBuildingUpgrade(
            cooldown.getCharacterId(), 
            cooldown.getTargetId()
        );
    }
    
    @Override
    public boolean supports(CooldownType type) {
        return CooldownType.BUILDING_UPGRADE.equals(type);
    }
} 