package com.xiuxian.xiuxianserver.dto;

import java.util.List;

/**
 * GeneralsTemplateUpdateRequestDTO类，用于传输更新武将模板的请求数据。
 */
public class GeneralsTemplateUpdateRequestDTO {
    private Long id;                      // 武将模板ID
    private String name;                  // 武将的名称
    private int rarity;                   // 武将的稀有度
    private int initialLevel;             // 初始等级
    private int initialStars;             // 初始星级
    private int strength;                 // 力量值
    private int intelligence;             // 智力值
    private int charisma;                 // 魅力值
    private int leadership;               // 统御值
    private int attack;                   // 攻击力
    private int defense;                  // 防御力
    private int troops;                   // 兵力值
    private int speed;                    // 速度
    private int attackPerLevel;           // 每级提升的攻击力
    private int defensePerLevel;          // 每级提升的防御力
    private int troopsPerLevel;           // 每级提升的兵力值
    private int attackPerTier;            // 每阶提升的攻击力
    private int defensePerTier;           // 每阶提升的防御力
    private int troopsPerTier;            // 每阶提升的兵力值
    private String normalTalentId;        // 普通天赋ID
    private String awakeningTalentId;     // 觉醒天赋ID
    private List<String> initialSkillIds; // 初始技能ID列表
    private Long frontTroopId;            // 前军兵种ID
    private Long rearTroopId;             // 后军兵种ID
    private String appearanceTemplateId;  // 外观模板ID
    private String description;           // 武将描述
    private String biography;             // 武将传记

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getInitialLevel() {
        return initialLevel;
    }

    public void setInitialLevel(int initialLevel) {
        this.initialLevel = initialLevel;
    }

    public int getInitialStars() {
        return initialStars;
    }

    public void setInitialStars(int initialStars) {
        this.initialStars = initialStars;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getTroops() {
        return troops;
    }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackPerLevel() {
        return attackPerLevel;
    }

    public void setAttackPerLevel(int attackPerLevel) {
        this.attackPerLevel = attackPerLevel;
    }

    public int getDefensePerLevel() {
        return defensePerLevel;
    }

    public void setDefensePerLevel(int defensePerLevel) {
        this.defensePerLevel = defensePerLevel;
    }

    public int getTroopsPerLevel() {
        return troopsPerLevel;
    }

    public void setTroopsPerLevel(int troopsPerLevel) {
        this.troopsPerLevel = troopsPerLevel;
    }

    public int getAttackPerTier() {
        return attackPerTier;
    }

    public void setAttackPerTier(int attackPerTier) {
        this.attackPerTier = attackPerTier;
    }

    public int getDefensePerTier() {
        return defensePerTier;
    }

    public void setDefensePerTier(int defensePerTier) {
        this.defensePerTier = defensePerTier;
    }

    public int getTroopsPerTier() {
        return troopsPerTier;
    }

    public void setTroopsPerTier(int troopsPerTier) {
        this.troopsPerTier = troopsPerTier;
    }

    public String getNormalTalentId() {
        return normalTalentId;
    }

    public void setNormalTalentId(String normalTalentId) {
        this.normalTalentId = normalTalentId;
    }

    public String getAwakeningTalentId() {
        return awakeningTalentId;
    }

    public void setAwakeningTalentId(String awakeningTalentId) {
        this.awakeningTalentId = awakeningTalentId;
    }

    public List<String> getInitialSkillIds() {
        return initialSkillIds;
    }

    public void setInitialSkillIds(List<String> initialSkillIds) {
        this.initialSkillIds = initialSkillIds;
    }

    public Long getFrontTroopId() {
        return frontTroopId;
    }

    public void setFrontTroopId(Long frontTroopId) {
        this.frontTroopId = frontTroopId;
    }

    public Long getRearTroopId() {
        return rearTroopId;
    }

    public void setRearTroopId(Long rearTroopId) {
        this.rearTroopId = rearTroopId;
    }

    public String getAppearanceTemplateId() {
        return appearanceTemplateId;
    }

    public void setAppearanceTemplateId(String appearanceTemplateId) {
        this.appearanceTemplateId = appearanceTemplateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
