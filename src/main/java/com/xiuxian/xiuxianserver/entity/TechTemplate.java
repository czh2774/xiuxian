package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 科技模板表，存储科技的基础信息，包括名称、描述、类型和最大等级。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "tech_template")
@Schema(description = "科技模板实体")
public class TechTemplate {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '科技唯一标识符'")
    @ExcelColumn(headerName = "科技模板ID", comment = "科技唯一标识符")
    @Schema(description = "科技唯一标识符")
    private Long id; // 科技模板ID

    @Column(name = "name", nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '科技的名称'")
    @Schema(description = "科技名称", example = "高级农业")
    @ExcelColumn(headerName = "科技名称", comment = "科技的名称")
    private String name; // 科技名称

    @Column(name = "description", length = 500, columnDefinition = "VARCHAR(500) COMMENT '科技的描述'")
    @Schema(description = "科技描述", example = "提高粮食产量的农业科技")
    @ExcelColumn(headerName = "科技描述", comment = "科技的描述")
    private String description; // 科技描述

    @Column(name = "tech_type", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '科技的类型'")
    @Schema(description = "科技类型", example = "资源类")
    @ExcelColumn(headerName = "科技类型", comment = "科技的类型")
    private String techType; // 科技类型

    @Column(name = "max_level", nullable = false, columnDefinition = "INT COMMENT '科技的最大等级'")
    @Schema(description = "最大等级", example = "5")
    @ExcelColumn(headerName = "最大等级", comment = "科技的最大等级")
    private int maxLevel; // 最大等级
}
