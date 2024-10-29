package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import java.time.LocalDateTime;

/**
 * CharacterBuildingDTO
 * 用于传输角色建筑实例的信息
 */
public class CharacterBuildingDTO {

    private Long characterBuildingId;  // 建筑实例ID
    private Long characterId;  // 角色ID
    private Long buildingTemplateId;  // 建筑模板ID
    private Long locationId;  // 位置ID
    private int currentLevel;  // 建筑等级
    private BuildingStatusType buildingStatus;  // 建筑状态
    private LocalDateTime actionStartTime;  // 操作开始时间
    private int actionTotalDuration;  // 操作总计时间（秒）
    private Long transactionId;  // 事务ID
    private FeaturePromptType featurePrompt;  // 功能提示
    private boolean rewardPending;  // 是否有待领取奖励
    private String growthType;  // 增长类型
    private Long growthId;  // 增长实例ID
    private int growthValue;  // 增长的数值或等级

    // Constructors, Getters and Setters
    public CharacterBuildingDTO() {}

    public CharacterBuildingDTO(Long characterBuildingId, Long characterId, Long buildingTemplateId, Long locationId,
                                int currentLevel, BuildingStatusType buildingStatus, LocalDateTime actionStartTime,
                                int actionTotalDuration, Long transactionId, FeaturePromptType featurePrompt,
                                boolean rewardPending, String growthType, Long growthId, int growthValue) {
        this.characterBuildingId = characterBuildingId;
        this.characterId = characterId;
        this.buildingTemplateId = buildingTemplateId;
        this.locationId = locationId;
        this.currentLevel = currentLevel;
        this.buildingStatus = buildingStatus;
        this.actionStartTime = actionStartTime;
        this.actionTotalDuration = actionTotalDuration;
        this.transactionId = transactionId;
        this.featurePrompt = featurePrompt;
        this.rewardPending = rewardPending;
        this.growthType = growthType;
        this.growthId = growthId;
        this.growthValue = growthValue;
    }

    // Getters and Setters
    public Long getCharacterBuildingId() {
        return characterBuildingId;
    }

    public void setCharacterBuildingId(Long characterBuildingId) {
        this.characterBuildingId = characterBuildingId;
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

    public LocalDateTime getActionStartTime() {
        return actionStartTime;
    }

    public void setActionStartTime(LocalDateTime actionStartTime) {
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
}
