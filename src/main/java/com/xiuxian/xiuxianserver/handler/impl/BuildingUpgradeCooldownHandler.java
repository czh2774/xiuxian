package com.xiuxian.xiuxianserver.handler.impl;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.handler.CooldownCompletionHandler;
import com.xiuxian.xiuxianserver.manager.CharacterBuildingManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BuildingUpgradeCooldownHandler implements CooldownCompletionHandler {
    
    private final CharacterBuildingManager buildingManager;
    
    @Override
    public void onCooldownComplete(Cooldown cooldown) {
        try {
            buildingManager.completeUpgrade(cooldown.getTargetId());
            log.info("Building upgrade completed - buildingId: {}, characterId: {}", 
                cooldown.getTargetId(), cooldown.getCharacterId());
        } catch (Exception e) {
            log.error("Failed to complete building upgrade - buildingId: {}, error: {}", 
                cooldown.getTargetId(), e.getMessage(), e);
        }
    }
    
    @Override
    public boolean supports(CooldownType type) {
        return CooldownType.BUILDING_UPGRADE == type;
    }
} 