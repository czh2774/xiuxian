package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 科技升级表，存储科技每一级的研究时间、资源消耗、研究条件和效果。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "tech_upgrade")
@Schema(description = "科技升级实体")
public class TechUpgrade {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '科技升级唯一标识符'")
    @ExcelColumn(headerName = "科技升级ID", comment = "科技升级唯一标识符")
    @Schema(description = "科技升级唯一标识符")
    private Long id; // 科技升级ID

    @Column(name = "tech_template_id", nullable = false, columnDefinition = "BIGINT COMMENT '关联的科技模板ID'")
    @Schema(description = "科技模板ID", example = "1")
    @ExcelColumn(headerName = "科技模板ID", comment = "关联的科技模板ID")
    private Long techTemplateId; // 科技模板ID（不使用外键）

    @Column(name = "level", nullable = false, columnDefinition = "INT COMMENT '科技的当前等级'")
    @Schema(description = "科技等级", example = "1")
    @ExcelColumn(headerName = "科技等级", comment = "科技的当前等级")
    private int level; // 科技等级

    @Column(name = "research_time", nullable = false, columnDefinition = "INT COMMENT '研究该科技等级所需的时间，单位为秒'")
    @Schema(description = "研究时间", example = "1800")
    @ExcelColumn(headerName = "研究时间", comment = "研究该科技等级所需的时间，单位为秒")
    private int researchTime; // 研究时间

    @Column(name = "research_requirements", length = 500, columnDefinition = "VARCHAR(500) COMMENT '研究该等级科技的前置条件，JSON格式'")
    @Schema(description = "研究条件", example = "{\"required_buildings\": [{\"building_id\": 1, \"level\": 5}], \"required_techs\": [{\"tech_id\": 2}]}")
    @ExcelColumn(headerName = "研究条件", comment = "研究该等级科技的前置条件，JSON格式")
    private String researchRequirements; // 研究条件（JSON格式）

    @Column(name = "resource_cost", length = 500, columnDefinition = "VARCHAR(500) COMMENT '研究该等级科技所需的资源，JSON格式'")
    @Schema(description = "资源消耗", example = "{\"wood\": 500, \"iron\": 300}")
    @ExcelColumn(headerName = "资源消耗", comment = "研究该等级科技所需的资源，JSON格式")
    private String resourceCost; // 资源消耗（JSON格式）

    @Column(name = "effect", length = 500, columnDefinition = "VARCHAR(500) COMMENT '科技激活后的效果，JSON格式'")
    @Schema(description = "科技效果", example = "{\"resource_bonus\": {\"food\": 10}}")
    @ExcelColumn(headerName = "科技效果", comment = "科技激活后的效果，JSON格式")
    private String effect; // 科技效果（JSON格式）
}
