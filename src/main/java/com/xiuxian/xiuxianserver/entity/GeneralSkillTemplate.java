package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.enums.GeneralType;

/**
 * 武将技能模板，存储武将的技能信息。
 */
@ExcelField
@Data
@Entity
@Table(name = "general_skill_template")
@Schema(description = "武将技能模板实体")
public class GeneralSkillTemplate {

    @Id
    @Schema(description = "技能ID")
    @ExcelColumn(headerName = "技能ID", comment = "技能的唯一标识符")
    private Long id;

    @Schema(description = "技能名称", example = "狂暴斩")
    @ExcelColumn(headerName = "技能名称", comment = "技能的名称")
    private String name;

    @Schema(description = "技能描述", example = "狂暴斩造成大量伤害")
    @ExcelColumn(headerName = "技能描述", comment = "技能的详细描述")
    private String description;

    @Schema(description = "技能效果", example = "{\"damage\": 300}")
    @ExcelColumn(headerName = "技能效果", comment = "技能的效果，JSON格式存储")
    private String effect;

    @Schema(description = "技能目标", example = "敌方单体")
    @ExcelColumn(headerName = "目标类型", comment = "技能的目标类型")
    private String targetType;

    @Schema(description = "技能解锁等级", example = "10")
    @ExcelColumn(headerName = "解锁等级", comment = "武将解锁技能所需的等级")
    private int unlockLevel;

    @Schema(description = "适用的武将类型", example = "WEN_GUAN")
    @ExcelColumn(headerName = "武将类型", comment = "技能适用的武将类型")
    private GeneralType generalType;
}
