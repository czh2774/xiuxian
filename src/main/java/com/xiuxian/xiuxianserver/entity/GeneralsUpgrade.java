package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.annotations.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 武将升级表，存储武将每一级的属性提升、经验要求和技能、天赋解锁。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "generals_upgrade")
@Schema(description = "武将升级实体")
public class GeneralsUpgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "武将升级唯一标识符")
    @ExcelColumn(headerName = "武将升级ID", comment = "武将升级记录唯一标识符")
    private Long generalUpgradeId;

    @Schema(description = "武将模板ID", example = "1")
    @ExcelColumn(headerName = "武将模板ID", comment = "关联的武将模板ID")
    private Long generalTemplateId;

    @Schema(description = "武将等级", example = "2")
    @ExcelColumn(headerName = "等级", comment = "武将的当前升级等级")
    private int level;

    @Schema(description = "升级所需经验", example = "1000")
    @ExcelColumn(headerName = "所需经验", comment = "升级到该等级所需的经验值")
    private int requiredExperience;

    @Schema(description = "攻击力提升", example = "10")
    @ExcelColumn(headerName = "攻击力提升", comment = "升级时增加的攻击力")
    private int attackIncrease;

    @Schema(description = "防御力提升", example = "8")
    @ExcelColumn(headerName = "防御力提升", comment = "升级时增加的防御力")
    private int defenseIncrease;

    @Schema(description = "兵力提升", example = "50")
    @ExcelColumn(headerName = "兵力提升", comment = "升级时增加的兵力")
    private int troopsIncrease;

    @Schema(description = "速度提升", example = "5")
    @ExcelColumn(headerName = "速度提升", comment = "升级时增加的速度")
    private int speedIncrease;

    @Schema(description = "解锁的技能", example = "[\"skill_1\", \"skill_2\"]")
    @ExcelColumn(headerName = "解锁技能", comment = "升级时解锁的技能（JSON格式存储）")
    private String skillUnlock;

    @Schema(description = "解锁或进阶的天赋", example = "[\"talent_1\"]")
    @ExcelColumn(headerName = "解锁天赋", comment = "升级时解锁或进阶的天赋（JSON格式存储）")
    private String talentUnlock;
}
