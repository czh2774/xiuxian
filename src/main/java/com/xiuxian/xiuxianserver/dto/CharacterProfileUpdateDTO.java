package com.xiuxian.xiuxianserver.dto;

import lombok.Data;

@Data
public class CharacterProfileUpdateDTO {
    private Long characterId;  // 用于标识要更新的角色
    private Long playerId;
    private String name;
    private String faction;
    private String avatar;
    private String officialPosition;
    private Long governorCityId;
    private String status;
    private int maxBuildingUpgradeQueues;
    private int currentBuildingUpgradeQueues;
}
