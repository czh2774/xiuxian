package com.xiuxian.xiuxianserver.dto;

import lombok.Data;

@Data
public class CharacterGeneralDTO {
    private Long id;
    private Long characterId;
    private Long generalTemplateId;
    private Integer level;
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
