package com.xiuxian.xiuxianserver.dto;

/**
 * GeneralsUpgradeDTO类，用于传输武将升级数据。
 */
public class GeneralsUpgradeDTO {
    private Long generalUpgradeId;        // 武将升级唯一标识符
    private Long generalTemplateId;       // 关联的武将模板ID
    private int level;                    // 武将的当前升级等级
    private int requiredExperience;       // 升级到该等级所需的经验值
    private int attackIncrease;           // 升级时增加的攻击力
    private int defenseIncrease;          // 升级时增加的防御力
    private int troopsIncrease;           // 升级时增加的兵力值
    private int speedIncrease;            // 升级时增加的速度
    private String skillUnlock;           // 升级时解锁的技能（JSON格式）
    private String talentUnlock;          // 升级时解锁或进阶的天赋（JSON格式）

    // Getters and Setters
    public Long getGeneralUpgradeId() {
        return generalUpgradeId;
    }

    public void setGeneralUpgradeId(Long generalUpgradeId) {
        this.generalUpgradeId = generalUpgradeId;
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

    public int getRequiredExperience() {
        return requiredExperience;
    }

    public void setRequiredExperience(int requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }

    public void setAttackIncrease(int attackIncrease) {
        this.attackIncrease = attackIncrease;
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }

    public void setDefenseIncrease(int defenseIncrease) {
        this.defenseIncrease = defenseIncrease;
    }

    public int getTroopsIncrease() {
        return troopsIncrease;
    }

    public void setTroopsIncrease(int troopsIncrease) {
        this.troopsIncrease = troopsIncrease;
    }

    public int getSpeedIncrease() {
        return speedIncrease;
    }

    public void setSpeedIncrease(int speedIncrease) {
        this.speedIncrease = speedIncrease;
    }

    public String getSkillUnlock() {
        return skillUnlock;
    }

    public void setSkillUnlock(String skillUnlock) {
        this.skillUnlock = skillUnlock;
    }

    public String getTalentUnlock() {
        return talentUnlock;
    }

    public void setTalentUnlock(String talentUnlock) {
        this.talentUnlock = talentUnlock;
    }
}
