package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CharacterGeneralCreateRequestDTO {
    @NotNull(message = "Character ID is required")
    private Long characterId;
    
    @NotNull(message = "General template ID is required")
    private Long generalTemplateId;
    
    @NotNull(message = "Level is required")
    @Min(value = 1, message = "Level must be at least 1")
    private Integer level;
    
    @NotNull(message = "Stars is required")
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