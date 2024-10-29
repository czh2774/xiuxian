package com.xiuxian.xiuxianserver.dto;

/**
 * CharacterBuildingCreateRequestDTO
 * 用于创建角色建筑实例的请求体
 */
public class CharacterBuildingCreateRequestDTO {

    private Long characterId;  // 角色ID
    private Long buildingTemplateId;  // 建筑模板ID
    private Long locationId;  // 位置ID

    // Constructors, Getters and Setters
    public CharacterBuildingCreateRequestDTO() {}

    public CharacterBuildingCreateRequestDTO(Long characterId, Long buildingTemplateId, Long locationId) {
        this.characterId = characterId;
        this.buildingTemplateId = buildingTemplateId;
        this.locationId = locationId;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public Long getBuildingTemplateId() {
        return buildingTemplateId;
    }

    public void setBuildingTemplateId(Long buildingTemplateId) {
        this.buildingTemplateId = buildingTemplateId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
