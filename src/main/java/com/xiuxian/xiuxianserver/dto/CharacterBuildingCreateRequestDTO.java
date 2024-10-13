package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * CharacterBuildingCreateRequestDTO
 * 用于创建角色建筑实例的请求对象
 */
@Schema(description = "创建角色建筑实例的请求对象")
public class CharacterBuildingCreateRequestDTO {

    @Schema(description = "角色ID", example = "5001", required = true)
    private Long characterId;  // 角色ID

    @Schema(description = "建筑模板ID", example = "3001", required = true)
    private Long buildingTemplateId;  // 建筑模板ID

    @Schema(description = "建筑位置ID", example = "1", required = true)
    private Long locationId;  // 建筑位置ID

    @Schema(description = "事务ID", example = "2002", required = true)
    private Long transactionId;  // 事务ID

    // 无参构造函数
    public CharacterBuildingCreateRequestDTO() {
    }

    // 有参构造函数
    public CharacterBuildingCreateRequestDTO(Long characterId, Long buildingTemplateId, Long locationId, Long transactionId) {
        this.characterId = characterId;
        this.buildingTemplateId = buildingTemplateId;
        this.locationId = locationId;
        this.transactionId = transactionId;
    }

    // Getters 和 Setters
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
