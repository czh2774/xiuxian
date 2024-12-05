package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "建筑升级响应")
public class BuildingUpgradeResponse {
    @Schema(description = "建筑信息")
    private CharacterBuildingDTO building;
    
    @Schema(description = "冷却信息")
    private CooldownDTO cooldown;
} 