package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 建筑模板表，定义建筑的基础信息，如名称、是否可升级、最大等级等。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "building_template")
@Schema(description = "建筑模板实体")
public class BuildingTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "建筑模板唯一标识符")
    @ExcelColumn(headerName = "建筑模板ID", comment = "建筑唯一标识符")
    private Long buildingTemplateId; // 建筑模板ID

    @Schema(description = "建筑名称", example = "城墙")
    @ExcelColumn(headerName = "建筑名称", comment = "建筑的名称")
    private String name; // 建筑名称

    @Schema(description = "建筑描述", example = "城墙用于防御敌人的进攻")
    @ExcelColumn(headerName = "建筑描述", comment = "建筑的详细描述")
    private String description; // 建筑描述

    @Schema(description = "是否可升级", example = "true")
    @ExcelColumn(headerName = "是否可升级", comment = "建筑是否可升级")
    private boolean isUpgradeable; // 是否可升级

    @Schema(description = "建筑图标URL", example = "http://example.com/image.png")
    @ExcelColumn(headerName = "建筑图标URL", comment = "建筑的图标链接")
    private String imageUrl; // 建筑图标的URL

    @Schema(description = "建造所需的玩家等级", example = "1")
    @ExcelColumn(headerName = "所需玩家等级", comment = "建造该建筑的最低玩家等级")
    private int requiredPlayerLevel; // 建造该建筑的最低玩家等级

    @Schema(description = "最大等级", example = "10")
    @ExcelColumn(headerName = "最大等级", comment = "建筑的最大等级")
    private int maxLevel; // 建筑的最大等级


}
