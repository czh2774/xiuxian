package com.xiuxian.xiuxianserver.dto;

/**
 * BuildingTemplateDTO
 * 封装建筑模板的基本信息
 */
public class BuildingTemplateDTO {

    private Long buildingTemplateId;
    private String name;
    private String description;
    private int maxLevel;
    private boolean isUpgradeable;
    private String imageUrl;

    // Constructors
    public BuildingTemplateDTO() {
    }

    public BuildingTemplateDTO(Long buildingTemplateId, String name, String description, int maxLevel, boolean isUpgradeable, String imageUrl) {
        this.buildingTemplateId = buildingTemplateId;
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.isUpgradeable = isUpgradeable;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getBuildingTemplateId() {
        return buildingTemplateId;
    }

    public void setBuildingTemplateId(Long buildingTemplateId) {
        this.buildingTemplateId = buildingTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public boolean isUpgradeable() {
        return isUpgradeable;
    }

    public void setUpgradeable(boolean isUpgradeable) {
        this.isUpgradeable = isUpgradeable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
