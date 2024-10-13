package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * CharacterBuildingDTO
 * 用于传输角色建筑实例数据的DTO类
 */
@Schema(description = "角色建筑实例DTO")
public class CharacterBuildingDTO {

    @Schema(description = "建筑实例ID", example = "1001")
    private Long buildingId;  // 建筑实例ID

    @Schema(description = "角色ID", example = "5001")
    private Long characterId;  // 角色ID

    @Schema(description = "建筑模板ID", example = "3001")
    private Long buildingTemplateId;  // 建筑模板ID

    @Schema(description = "建筑位置ID", example = "1")
    private Long locationId;  // 位置ID

    @Schema(description = "建筑当前的等级", example = "1")
    private int currentLevel;  // 当前建筑的等级

    @Schema(description = "建筑状态", example = "IDLE")
    private BuildingStatusType buildingStatus;  // 建筑当前状态

    @Schema(description = "建筑操作开始时间（ISO 8601格式）", example = "2024-10-07T10:15:30")
    private String actionStartTime;  // 操作开始时间

    @Schema(description = "操作的总持续时间（以秒为单位）", example = "3600")
    private int actionTotalDuration;  // 操作总持续时间（秒）

    @Schema(description = "事务ID，用于标识当前的事务", example = "2002")
    private Long transactionId;  // 事务ID

    @Schema(description = "增长的类型，如兵力、科技、突破、道具等", example = "TECH")
    private String growthType;  // 增长的类型

    @Schema(description = "增长实例ID，如科技ID、道具ID等", example = "12001")
    private Long growthId;  // 增长实例ID

    @Schema(description = "增长的数值或等级", example = "5")
    private int growthValue;  // 增长的数值或等级

    @Schema(description = "功能提示类型", example = "COLLECT_FOOD")
    private FeaturePromptType featurePrompt;  // 功能提示类型

    @Schema(description = "是否有待领取的奖励", example = "false")
    private boolean rewardPending;  // 是否有待领取奖励

    // 构造函数
    public CharacterBuildingDTO(CharacterBuilding building) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null");
        }
        this.buildingId = building.getCharacterBuildingId();
        this.characterId = building.getCharacterId();
        this.buildingTemplateId = building.getBuildingTemplateId();
        this.locationId = building.getLocationId();
        this.currentLevel = building.getCurrentLevel();
        this.buildingStatus = building.getBuildingStatus();
        this.actionStartTime = building.getActionStartTime() != null ? building.getActionStartTime().toString() : null;
        this.actionTotalDuration = building.getActionTotalDuration();
        this.transactionId = building.getTransactionId();
        this.growthType = building.getGrowthType();
        this.growthId = building.getGrowthId();
        this.growthValue = building.getGrowthValue();
        this.featurePrompt = building.getFeaturePrompt();
        this.rewardPending = building.isRewardPending();
    }

    // Getters 和 Setters

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
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

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public BuildingStatusType getBuildingStatus() {
        return buildingStatus;
    }

    public void setBuildingStatus(BuildingStatusType buildingStatus) {
        this.buildingStatus = buildingStatus;
    }

    public String getActionStartTime() {
        return actionStartTime;
    }

    public void setActionStartTime(String actionStartTime) {
        this.actionStartTime = actionStartTime;
    }

    public int getActionTotalDuration() {
        return actionTotalDuration;
    }

    public void setActionTotalDuration(int actionTotalDuration) {
        this.actionTotalDuration = actionTotalDuration;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getGrowthType() {
        return growthType;
    }

    public void setGrowthType(String growthType) {
        this.growthType = growthType;
    }

    public Long getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Long growthId) {
        this.growthId = growthId;
    }

    public int getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(int growthValue) {
        this.growthValue = growthValue;
    }

    public FeaturePromptType getFeaturePrompt() {
        return featurePrompt;
    }

    public void setFeaturePrompt(FeaturePromptType featurePrompt) {
        this.featurePrompt = featurePrompt;
    }

    public boolean isRewardPending() {
        return rewardPending;
    }

    public void setRewardPending(boolean rewardPending) {
        this.rewardPending = rewardPending;
    }
}
