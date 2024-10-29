package com.xiuxian.xiuxianserver.dto;

import java.util.List;

/**
 * CharacterGeneralDTO类，用于传输角色武将实例的数据。
 */
public class CharacterGeneralDTO {
    private Long id;                      // 角色武将记录的唯一标识符
    private Long characterId;             // 角色ID
    private Long generalTemplateId;       // 武将模板ID
    private int level;                    // 武将当前等级
    private int stars;                    // 武将当前星级
    private int experience;               // 武将当前经验值
    private String status;                // 武将当前状态
    private List<String> equippedItems;   // 武将当前装备的ID列表
    private List<String> currentSkills;   // 武将当前技能的ID列表

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public Long getGeneralTemplateId() {
        return generalTemplateId;
    }

    public void setGeneralTemplateId(Long generalTemplateId) {
        this.generalTemplateId = generalTemplateId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(List<String> equippedItems) {
        this.equippedItems = equippedItems;
    }

    public List<String> getCurrentSkills() {
        return currentSkills;
    }

    public void setCurrentSkills(List<String> currentSkills) {
        this.currentSkills = currentSkills;
    }
}
