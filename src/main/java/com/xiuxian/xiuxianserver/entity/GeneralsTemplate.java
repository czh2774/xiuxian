package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.StringListConverter;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import java.util.List;

/**
 * 武将模板类，定义武将的基础信息，如名称、稀有度、初始等级、属性等。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "generals_template")
@Schema(description = "武将模板实体")
public class GeneralsTemplate {

    @Id
    @Schema(description = "武将模板唯一标识符")
    @ExcelColumn(headerName = "ID", comment = "武将唯一标识符")
    private Long id;

    @Schema(description = "武将名称", example = "关羽")
    @ExcelColumn(headerName = "武将名称", comment = "武将的名称")
    private String name;

    @Schema(description = "武将稀有度", example = "5")
    @ExcelColumn(headerName = "稀有度", comment = "武将的稀有度")
    private int rarity;

    @Schema(description = "武将初始等级", example = "1")
    @ExcelColumn(headerName = "初始等级", comment = "武将的初始等级")
    private int initialLevel;

    @Schema(description = "武将初始星级", example = "5")
    @ExcelColumn(headerName = "初始星级", comment = "武将的初始星级")
    private int initialStars;

    @Schema(description = "力量值", example = "85")
    @ExcelColumn(headerName = "力量", comment = "武将的力量值")
    private int strength;

    @Schema(description = "智力值", example = "90")
    @ExcelColumn(headerName = "智力", comment = "武将的智力值")
    private int intelligence;

    @Schema(description = "魅力值", example = "75")
    @ExcelColumn(headerName = "魅力", comment = "武将的魅力值")
    private int charisma;

    @Schema(description = "统御值", example = "80")
    @ExcelColumn(headerName = "统御", comment = "武将的统御值")
    private int leadership;

    @Schema(description = "攻击", example = "200")
    @ExcelColumn(headerName = "攻击", comment = "武将的攻击力")
    private int attack;

    @Schema(description = "防御", example = "150")
    @ExcelColumn(headerName = "防御", comment = "武将的防御力")
    private int defense;

    @Schema(description = "速度", example = "180")
    @ExcelColumn(headerName = "速度", comment = "武将的速度")
    private int speed;

    @Schema(description = "兵力", example = "3000")
    @ExcelColumn(headerName = "兵力", comment = "武将的兵力值")
    private int troops;

    @Schema(description = "格挡率", example = "10")
    @ExcelColumn(headerName = "格挡率", comment = "武将的格挡率（百分比）")
    private int blockRate;

    @Schema(description = "暴击率", example = "20")
    @ExcelColumn(headerName = "暴击率", comment = "武将的暴击率（百分比）")
    private int criticalHitRate;

    @Schema(description = "每级攻击力提升", example = "5")
    @ExcelColumn(headerName = "每级攻击力提升", comment = "每级提升的攻击力")
    private int attackPerLevel;

    @Schema(description = "每级防御力提升", example = "3")
    @ExcelColumn(headerName = "每级防御力提升", comment = "每级提升的防御力")
    private int defensePerLevel;

    @Schema(description = "每级兵力值提升", example = "50")
    @ExcelColumn(headerName = "每级兵力值提升", comment = "每级提升的兵力值")
    private int troopsPerLevel;

    @Schema(description = "每阶攻击力提升", example = "20")
    @ExcelColumn(headerName = "每阶攻击力提升", comment = "每阶提升的攻击力")
    private int attackPerTier;

    @Schema(description = "每阶防御力提升", example = "15")
    @ExcelColumn(headerName = "每阶防御力提升", comment = "每阶提升的防御力")
    private int defensePerTier;

    @Schema(description = "每阶兵力值提升", example = "200")
    @ExcelColumn(headerName = "每阶兵力值提升", comment = "每阶提升的兵力值")
    private int troopsPerTier;

    @Schema(description = "普通天赋ID", example = "UUID", nullable = true)
    @ExcelColumn(headerName = "普通天赋ID", comment = "武将的普通天赋ID，可以为空")
    private String normalTalentId;

    @Schema(description = "觉醒天赋ID", example = "UUID", nullable = true)
    @ExcelColumn(headerName = "觉醒天赋ID", comment = "武将的觉醒天赋ID，可以为空")
    private String awakeningTalentId;

    @Schema(description = "初始技能ID列表")
    @ExcelColumn(headerName = "初始技能", comment = "武将的初始技能ID列表")
    @Convert(converter = StringListConverter.class)
    private List<String> initialSkillIds;

    @Schema(description = "前军兵种ID", example = "1")
    @ExcelColumn(headerName = "前军兵种ID", comment = "武将的前军初始兵种ID")
    private Long frontTroopId;

    @Schema(description = "后军兵种ID", example = "2")
    @ExcelColumn(headerName = "后军兵种ID", comment = "武将的后军初始兵种ID")
    private Long rearTroopId;

    @Schema(description = "外观模板ID", example = "UUID")
    @ExcelColumn(headerName = "外观模板ID", comment = "武将的外观模板ID")
    private String appearanceTemplateId;

    @Schema(description = "武将描述", example = "关羽是三国时期蜀汉名将")
    @ExcelColumn(headerName = "描述", comment = "武将的详细描述")
    private String description;

    @Schema(description = "武将的传记", example = "关羽是三国时期蜀汉名将")
    @ExcelColumn(headerName = "传记", comment = "武将的传记描述")
    private String biography;
}
