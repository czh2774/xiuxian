package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CharacterBuildingDTO {
    private Long id;
    private Long characterId;
    private Long buildingTemplateId;
    private Long locationId;
    private int currentLevel;
    private BuildingStatusType buildingStatus;
    private LocalDateTime actionStartTime;
    private LocalDateTime lastModifiedTime;
}
