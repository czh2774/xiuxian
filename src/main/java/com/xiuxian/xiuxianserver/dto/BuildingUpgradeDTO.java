package com.xiuxian.xiuxianserver.dto;

/**
 * BuildingUpgradeDTO
 * 用于传输建筑升级的相关信息。
 */
public class BuildingUpgradeDTO {
    private Long buildingUpgradeId;
    private Long buildingTemplateId;
    private int level;
    private int upgradeTime;
    private int foodCost;
    private int woodCost;
    private int ironCost;

    // Constructors
    public BuildingUpgradeDTO() {}

    public BuildingUpgradeDTO(Long buildingUpgradeId, Long buildingTemplateId, int level, int upgradeTime, int foodCost, int woodCost, int ironCost, String effect) {
        this.buildingUpgradeId = buildingUpgradeId;
        this.buildingTemplateId = buildingTemplateId;
        this.level = level;
        this.upgradeTime = upgradeTime;
        this.foodCost = foodCost;
        this.woodCost = woodCost;
        this.ironCost = ironCost;
    }

    public BuildingUpgradeDTO(Long id, Long buildingTemplateId, int level, int upgradeTime, int foodCost, int woodCost, int ironCost) {
    }

    // Getters and Setters
    public Long getBuildingUpgradeId() {
        return buildingUpgradeId;
    }

    public void setBuildingUpgradeId(Long buildingUpgradeId) {
        this.buildingUpgradeId = buildingUpgradeId;
    }

    public Long getBuildingTemplateId() {
        return buildingTemplateId;
    }

    public void setBuildingTemplateId(Long buildingTemplateId) {
        this.buildingTemplateId = buildingTemplateId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpgradeTime() {
        return upgradeTime;
    }

    public void setUpgradeTime(int upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    public int getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(int foodCost) {
        this.foodCost = foodCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public void setWoodCost(int woodCost) {
        this.woodCost = woodCost;
    }

    public int getIronCost() {
        return ironCost;
    }

    public void setIronCost(int ironCost) {
        this.ironCost = ironCost;
    }


}
