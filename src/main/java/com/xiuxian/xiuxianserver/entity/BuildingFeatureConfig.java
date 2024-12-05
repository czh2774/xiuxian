package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.enums.BuildingFeatureType;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

/**
 * 建筑功能配置表，定义建筑的功能入口配置，如显示等级、使用等级等。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "building_feature_config")
@Schema(description = "建筑功能配置实体")
@NoArgsConstructor
public class BuildingFeatureConfig {

    @Id
    @Schema(description = "功能配置唯一标识符")
    @ExcelColumn(headerName = "功能配置ID", comment = "功能配置唯一标识符")
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '功能配置唯一标识符'")
    private Long id;

    @Schema(description = "建筑模板ID", example = "1")
    @ExcelColumn(headerName = "建筑模板ID", comment = "关联的建筑模板ID")
    @Column(name = "building_template_id", nullable = false, columnDefinition = "BIGINT COMMENT '关联的建筑模板ID'")
    private Long buildingTemplateId;

    @Schema(description = "功能类型", example = "UPGRADE")
    @ExcelColumn(headerName = "功能类型", comment = "建筑功能类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "feature_type", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '建筑功能类型'")
    private BuildingFeatureType featureType;

    @Schema(description = "显示等级", example = "1")
    @ExcelColumn(headerName = "显示等级", comment = "功能显示所需的角色等级")
    @Column(name = "display_level", nullable = false, columnDefinition = "INT COMMENT '功能显示所需的角色等级'")
    private Integer displayLevel;

    @Schema(description = "使用等级", example = "5")
    @ExcelColumn(headerName = "使用等级", comment = "功能使用所需的角色等级")
    @Column(name = "use_level", nullable = false, columnDefinition = "INT COMMENT '功能使用所需的角色等级'")
    private Integer useLevel;

    @Schema(description = "功能图标URL", example = "http://example.com/icons/upgrade.png")
    @ExcelColumn(headerName = "图标URL", comment = "功能的图标资源链接")
    @Column(name = "icon_url", columnDefinition = "VARCHAR(255) COMMENT '功能的图标资源链接'")
    private String iconUrl;
} 