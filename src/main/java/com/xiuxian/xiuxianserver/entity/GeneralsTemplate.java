package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.GeneralRankEnum;
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
    @Column(name = "rank", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '武将的品级'")
    @Schema(description = "武将品级", example = "TALENTED")
    @ExcelColumn(headerName = "品级", comment = "武将的品级")
    private GeneralRankEnum rank;



    @Column(name = "sort_order", nullable = false, columnDefinition = "INT COMMENT '手动排序字段'")
    @Schema(description = "手动排序字段", example = "10")
    @ExcelColumn(headerName = "排序值", comment = "手动设置的排序字段")
    private int sortOrder;



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

    @Column(name = "image_url", length = 255, columnDefinition = "VARCHAR(255) COMMENT '武将的图片URL'")
    @Schema(description = "武将图片URL", example = "http://example.com/image.jpg")
    @ExcelColumn(headerName = "图片URL", comment = "武将的图片URL")
    private String imageUrl;
}
