package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.enums.TroopType;

/**
 * 兵种技能模板，存储兵种的技能信息。
 */
@ExcelField
@Data
@Entity
@Table(name = "troop_skill_template")
@Schema(description = "兵种技能模板实体")
public class TroopSkillTemplate {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '技能的唯一标识符'")
    @Schema(description = "技能ID")
    @ExcelColumn(headerName = "技能ID", comment = "技能的唯一标识符")
    private Long id;

    @Column(name = "name", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '技能的名称'")
    @Schema(description = "技能名称", example = "铁壁防御")
    @ExcelColumn(headerName = "技能名称", comment = "技能的名称")
    private String name;

    @Column(name = "description", length = 500, columnDefinition = "VARCHAR(500) COMMENT '技能的详细描述'")
    @Schema(description = "技能描述", example = "提升所有兵种的防御力")
    @ExcelColumn(headerName = "技能描述", comment = "技能的详细描述")
    private String description;

    @Column(name = "effect", columnDefinition = "JSON COMMENT '技能的效果，JSON格式存储'")
    @Schema(description = "技能效果", example = "{\"defense\": 50}")
    @ExcelColumn(headerName = "技能效果", comment = "技能的效果，JSON格式存储")
    private String effect;

    @Column(name = "trigger_condition", columnDefinition = "JSON COMMENT '技能的触发条件，JSON格式存储'")
    @Schema(description = "技能触发条件", example = "{\"condition\": \"hp_below\", \"value\": 50}")
    @ExcelColumn(headerName = "触发条件", comment = "技能的触发条件，JSON格式存储")
    private String triggerCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "troop_type", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '技能适用的兵种类型'")
    @Schema(description = "适用的兵种类型", example = "INFANTRY")
    @ExcelColumn(headerName = "兵种类型", comment = "技能适用的兵种类型")
    private TroopType troopType;
}
