package com.xiuxian.xiuxianserver.dto.response;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class MainCityUpgradeResponse {
    private BuildingInfo building;
    private CharacterInfo character;
    private List<BuildingInfo> buildings;
    private List<RewardInfo> rewards;
    private Map<String, Integer> resourceChanges;
} 