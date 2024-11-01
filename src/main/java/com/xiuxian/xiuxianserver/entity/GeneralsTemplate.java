package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.StringListConverter;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.enums.RarityEnum;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 武将模板类，定义武将的基础信息，如名称、稀有度、初始等级、属性等。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@NoArgsConstructor
@Data
@Entity
@Table(name = "generals_template")
@Schema(description = "武将模板实体")
public class GeneralsTemplate {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '武将模板唯一标识符'")
    @Schema(description = "武将模板唯一标识符")
    @ExcelColumn(headerName = "ID", comment = "武将唯一标识符")
    private Long id;

    @Column(name = "name", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '武将的名称'")
    @Schema(description = "武将名称", example = "关羽")
    @ExcelColumn(headerName = "武将名称", comment = "武将的名称")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "rarity", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '武将的稀有度'")
    @Schema(description = "武将稀有度", example = "COMMON")
    @ExcelColumn(headerName = "稀有度", comment = "武将的稀有度")
    private RarityEnum rarity;

    @Column(name = "initial_level", nullable = false, columnDefinition = "INT COMMENT '武将的初始等级'")
    @Schema(description = "武将初始等级", example = "1")
    @ExcelColumn(headerName = "初始等级", comment = "武将的初始等级")
    private int initialLevel;

    @Column(name = "initial_stars", nullable = false, columnDefinition = "INT COMMENT '武将的初始星级'")
    @Schema(description = "武将初始星级", example = "5")
    @ExcelColumn(headerName = "初始星级", comment = "武将的初始星级")
    private int initialStars;

    // 其他字段保持不变
    @Column(name = "strength", nullable = false, columnDefinition = "INT COMMENT '武将的力量值'")
    @Schema(description = "力量值", example = "85")
    @ExcelColumn(headerName = "力量", comment = "武将的力量值")
    private int strength;

    @Column(name = "intelligence", nullable = false, columnDefinition = "INT COMMENT '武将的智力值'")
    @Schema(description = "智力值", example = "90")
    @ExcelColumn(headerName = "智力", comment = "武将的智力值")
    private int intelligence;

    @Column(name = "charisma", nullable = false, columnDefinition = "INT COMMENT '武将的魅力值'")
    @Schema(description = "魅力值", example = "75")
    @ExcelColumn(headerName = "魅力", comment = "武将的魅力值")
    private int charisma;

    @Column(name = "leadership", nullable = false, columnDefinition = "INT COMMENT '武将的统御值'")
    @Schema(description = "统御值", example = "80")
    @ExcelColumn(headerName = "统御", comment = "武将的统御值")
    private int leadership;

    @Column(name = "attack", nullable = false, columnDefinition = "INT COMMENT '武将的攻击力'")
    @Schema(description = "攻击", example = "200")
    @ExcelColumn(headerName = "攻击", comment = "武将的攻击力")
    private int attack;

    @Column(name = "defense", nullable = false, columnDefinition = "INT COMMENT '武将的防御力'")
    @Schema(description = "防御", example = "150")
    @ExcelColumn(headerName = "防御", comment = "武将的防御力")
    private int defense;

    @Column(name = "speed", nullable = false, columnDefinition = "INT COMMENT '武将的速度'")
    @Schema(description = "速度", example = "180")
    @ExcelColumn(headerName = "速度", comment = "武将的速度")
    private int speed;

    @Column(name = "troops", nullable = false, columnDefinition = "INT COMMENT '武将的兵力值'")
    @Schema(description = "兵力", example = "3000")
    @ExcelColumn(headerName = "兵力", comment = "武将的兵力值")
    private int troops;

    @Column(name = "block_rate", nullable = false, columnDefinition = "INT COMMENT '武将的格挡率（百分比）'")
    @Schema(description = "格挡率", example = "10")
    @ExcelColumn(headerName = "格挡率", comment = "武将的格挡率（百分比）")
    private int blockRate;

    @Column(name = "critical_hit_rate", nullable = false, columnDefinition = "INT COMMENT '武将的暴击率（百分比）'")
    @Schema(description = "暴击率", example = "20")
    @ExcelColumn(headerName = "暴击率", comment = "武将的暴击率（百分比）")
    private int criticalHitRate;

    @Column(name = "attack_per_level", nullable = false, columnDefinition = "INT COMMENT '每级提升的攻击力'")
    @Schema(description = "每级攻击力提升", example = "5")
    @ExcelColumn(headerName = "每级攻击力提升", comment = "每级提升的攻击力")
    private int attackPerLevel;

    @Column(name = "defense_per_level", nullable = false, columnDefinition = "INT COMMENT '每级提升的防御力'")
    @Schema(description = "每级防御力提升", example = "3")
    @ExcelColumn(headerName = "每级防御力提升", comment = "每级提升的防御力")
    private int defensePerLevel;

    @Column(name = "troops_per_level", nullable = false, columnDefinition = "INT COMMENT '每级提升的兵力值'")
    @Schema(description = "每级兵力值提升", example = "50")
    @ExcelColumn(headerName = "每级兵力值提升", comment = "每级提升的兵力值")
    private int troopsPerLevel;

    @Column(name = "attack_per_tier", nullable = false, columnDefinition = "INT COMMENT '每阶提升的攻击力'")
    @Schema(description = "每阶攻击力提升", example = "20")
    @ExcelColumn(headerName = "每阶攻击力提升", comment = "每阶提升的攻击力")
    private int attackPerTier;

    @Column(name = "defense_per_tier", nullable = false, columnDefinition = "INT COMMENT '每阶提升的防御力'")
    @Schema(description = "每阶防御力提升", example = "15")
    @ExcelColumn(headerName = "每阶防御力提升", comment = "每阶提升的防御力")
    private int defensePerTier;

    @Column(name = "troops_per_tier", nullable = false, columnDefinition = "INT COMMENT '每阶提升的兵力值'")
    @Schema(description = "每阶兵力值提升", example = "200")
    @ExcelColumn(headerName = "每阶兵力值提升", comment = "每阶提升的兵力值")
    private int troopsPerTier;

    @Column(name = "normal_talent_id", length = 36, columnDefinition = "VARCHAR(36) COMMENT '武将的普通天赋ID，可以为空'")
    @Schema(description = "普通天赋ID", example = "UUID", nullable = true)
    @ExcelColumn(headerName = "普通天赋ID", comment = "武将的普通天赋ID，可以为空")
    private String normalTalentId;

    @Column(name = "awakening_talent_id", length = 36, columnDefinition = "VARCHAR(36) COMMENT '武将的觉醒天赋ID，可以为空'")
    @Schema(description = "觉醒天赋ID", example = "UUID", nullable = true)
    @ExcelColumn(headerName = "觉醒天赋ID", comment = "武将的觉醒天赋ID，可以为空")
    private String awakeningTalentId;

    @Convert(converter = StringListConverter.class)
    @Column(name = "initial_skill_ids", columnDefinition = "TEXT COMMENT '武将的初始技能ID列表'")
    @Schema(description = "初始技能ID列表")
    @ExcelColumn(headerName = "初始技能", comment = "武将的初始技能ID列表")
    private List<String> initialSkillIds;

    @Column(name = "front_troop_id", columnDefinition = "BIGINT COMMENT '武将的前军初始兵种ID'")
    @Schema(description = "前军兵种ID", example = "1")
    @ExcelColumn(headerName = "前军兵种ID", comment = "武将的前军初始兵种ID")
    private Long frontTroopId;

    @Column(name = "rear_troop_id", columnDefinition = "BIGINT COMMENT '武将的后军初始兵种ID'")
    @Schema(description = "后军兵种ID", example = "2")
    @ExcelColumn(headerName = "后军兵种ID", comment = "武将的后军初始兵种ID")
    private Long rearTroopId;

    @Column(name = "appearance_template_id", length = 36, columnDefinition = "VARCHAR(36) COMMENT '武将的外观模板ID'")
    @Schema(description = "外观模板ID", example = "UUID")
    @ExcelColumn(headerName = "外观模板ID", comment = "武将的外观模板ID")
    private String appearanceTemplateId;

    @Column(name = "description", length = 500, columnDefinition = "VARCHAR(500) COMMENT '武将的详细描述'")
    @Schema(description = "武将描述", example = "关羽是三国时期蜀汉名将")
    @ExcelColumn(headerName = "描述", comment = "武将的详细描述")
    private String description;

    @Column(name = "biography", length = 2000, columnDefinition = "VARCHAR(2000) COMMENT '武将的传记描述'")
    @Schema(description = "武将的传记", example = "关羽是三国时期蜀汉名将")
    @ExcelColumn(headerName = "传记", comment = "武将的传记描述")
    private String biography;
}
