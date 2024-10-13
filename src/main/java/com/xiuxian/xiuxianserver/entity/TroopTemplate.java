package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 兵种模板表，存储兵种的基础信息，包括名称、描述、属性和兵阶。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "troop_template")
@Schema(description = "兵种模板实体")
public class TroopTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ExcelColumn(headerName = "兵种模板ID", comment = "兵种唯一标识符")
    private Long troopTemplateId; // 兵种模板ID

    @Schema(description = "兵种名称", example = "骑兵")
    @ExcelColumn(headerName = "兵种名称", comment = "兵种的名称")
    private String name; // 兵种名称

    @Schema(description = "兵种描述", example = "快速且强力的近战单位")
    @ExcelColumn(headerName = "兵种描述", comment = "兵种的描述")
    private String description; // 兵种描述

    @Schema(description = "兵种类型", example = "近战")
    @ExcelColumn(headerName = "兵种类型", comment = "兵种的类型")
    private String troopType; // 兵种类型

    @Schema(description = "基础攻击力", example = "150")
    @ExcelColumn(headerName = "基础攻击力", comment = "兵种的基础攻击力")
    private int baseAttack; // 基础攻击力

    @Schema(description = "基础防御力", example = "100")
    @ExcelColumn(headerName = "基础防御力", comment = "兵种的基础防御力")
    private int baseDefense; // 基础防御力

    @Schema(description = "基础生命值", example = "1200")
    @ExcelColumn(headerName = "基础生命值", comment = "兵种的基础生命值")
    private int baseHealth; // 基础生命值

    @Schema(description = "基础速度", example = "300")
    @ExcelColumn(headerName = "基础速度", comment = "兵种的基础速度")
    private int baseSpeed; // 基础速度

    @Schema(description = "最大兵阶", example = "3")
    @ExcelColumn(headerName = "最大兵阶", comment = "兵种的最大兵阶")
    private int maxTier; // 最大兵阶
}
