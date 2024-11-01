package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.xiuxian.xiuxianserver.enums.RarityEnum; // 引入 RarityEnum

import java.util.List;

/**
 * GeneralsTemplateDTO类，用于传输武将模板的数据。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralsTemplateDTO {
    private Long id;                      // 武将模板ID
    private String name;                  // 武将的名称
    private RarityEnum rarity;            // 武将的稀有度，调整为 RarityEnum 类型
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
}
