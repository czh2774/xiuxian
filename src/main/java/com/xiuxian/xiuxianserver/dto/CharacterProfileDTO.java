package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CharacterProfileDTO {
    private Long id;
    private Long playerId;
    private String name;
    private String faction;
    private int level;
    private String avatar;
    private int combatPower;
    private int titleLevel;
    private String titlePrivileges;
    private String officialPosition;
    private Long governorCityId;
    private int yuanbao;
    private int warMerits;
    private int reputation;
    private int copperCoins;
    private int food;
    private int wood;
    private int ironOre;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int maxBuildingUpgradeQueues;
    private int currentBuildingUpgradeQueues;
}
