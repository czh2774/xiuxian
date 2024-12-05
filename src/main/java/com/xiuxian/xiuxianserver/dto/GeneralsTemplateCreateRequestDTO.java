package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.enums.GeneralRankEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class GeneralsTemplateCreateRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Rank is required")
    private GeneralRankEnum rank;
    
    private int sortOrder;
    private int strength;
    private int intelligence;
    private int charisma;
    private int leadership;
    private int attack;
    private int defense;
    private int speed;
    private int troops;
    private String normalTalentId;
    private String awakeningTalentId;
    private List<String> initialSkillIds;
    private Long frontTroopId;
    private Long rearTroopId;
    private String appearanceTemplateId;
    private String description;
    private String biography;
    private String imageUrl;
}
