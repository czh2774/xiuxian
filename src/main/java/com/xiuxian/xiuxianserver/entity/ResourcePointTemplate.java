package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.ResourceTypeEnum;
import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.annotations.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 资源点模板表，定义资源点的基础信息，如类型、产量、存储时间等。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "resource_point_template")
@Schema(description = "资源点模板实体")
public class ResourcePointTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @ExcelColumn(headerName = "资源点ID", comment = "资源点唯一标识符")
    private Long resourcePointId; // 资源点ID

    @Schema(description = "资源点名称", example = "伐木场")
    @ExcelColumn(headerName = "资源点名称", comment = "资源点的名称")
    private String name; // 资源点名称

    @Schema(description = "资源点描述", example = "一个生产木材的采集点。")
    @ExcelColumn(headerName = "资源点描述", comment = "资源点的详细描述")
    private String description; // 资源点描述

    @Schema(description = "资源类型", example = "WOOD")
    @ExcelColumn(headerName = "资源类型", comment = "资源点的资源类型")
    @Enumerated(EnumType.STRING)
    private ResourceTypeEnum resourceType; // 资源类型（枚举类）

    @Schema(description = "每小时产量", example = "200")
    @ExcelColumn(headerName = "每小时产量", comment = "资源点每小时的资源基础产量")
    private int resourceProductionRate; // 每小时产量

    @Schema(description = "最大存储时间（小时）", example = "10")
    @ExcelColumn(headerName = "最大存储时间（小时）", comment = "资源点最大存储时间，单位为小时")
    private int maxStorageHours; // 最大存储时间（小时）

    @Schema(description = "采集时间", example = "5")
    @ExcelColumn(headerName = "采集时间", comment = "采集资源所需的时间（分钟）")
    private int gatheringTime; // 采集时间

    @Schema(description = "资源点等级", example = "3")
    @ExcelColumn(headerName = "资源点等级", comment = "资源点的等级")
    private int resourcePointLevel; // 资源点等级

    @Schema(description = "资源点坐标", example = "(300, 450)")
    @ExcelColumn(headerName = "坐标", comment = "资源点在地图上的位置坐标")
    private String coordinates; // 资源点坐标
}
