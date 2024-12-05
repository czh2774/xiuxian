package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CharacterGeneralUpdateRequestDTO {
    private Long id;
    private Long characterId;
    private Long generalTemplateId;
    
    @Min(value = 1, message = "Level must be at least 1")
    private Integer level;
    
    @Min(value = 1, message = "Stars must be at least 1")
    private Integer stars;
    
    private Integer attack;
    private Integer defense;
    private Integer troops;
    private Integer speed;
    private String normalTalentId;
    private String awakeningTalentId;
    private Long frontTroopId;
    private Long rearTroopId;
    private Boolean isInFormation;
    private Integer formationPosition;
} 