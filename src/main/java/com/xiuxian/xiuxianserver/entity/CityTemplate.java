package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.CityLevelEnum;
import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 城市模板表，定义城市的基本信息、等级、税金等。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "city_template")
@Schema(description = "城市模板实体")
public class CityTemplate {

    @Id
    @Schema(description = "城市唯一标识符")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '城市唯一标识符'")
    @ExcelColumn(headerName = "城市模板ID", comment = "城市唯一标识符")
    private Long id; // 城市模板ID

    @Schema(description = "城市名称", example = "洛阳")
    @ExcelColumn(headerName = "城市名称", comment = "城市的名称")
    @Column(name = "name", columnDefinition = "VARCHAR(100) COMMENT '城市名称'")
    private String name; // 城市名称

    @Schema(description = "城市描述", example = "东汉时期的都城，历史悠久，战略位置优越。")
    @ExcelColumn(headerName = "城市描述", comment = "城市的详细描述")
    @Column(name = "description", columnDefinition = "TEXT COMMENT '城市的详细描述'")
    private String description; // 城市描述

    @Schema(description = "隶属势力ID", example = "1")
    @ExcelColumn(headerName = "隶属势力ID", comment = "该城市所属的势力ID")
    @Column(name = "faction_id", columnDefinition = "BIGINT COMMENT '该城市所属的势力ID'")
    private Long factionId; // 隶属势力ID

    @Schema(description = "地图坐标", example = "(200, 150)")
    @ExcelColumn(headerName = "坐标", comment = "城市在地图上的坐标")
    @Column(name = "coordinates", columnDefinition = "VARCHAR(50) COMMENT '城市在地图上的坐标'")
    private String coordinates; // 城市坐标 (x, y)

    @Schema(description = "建筑容量", example = "50")
    @ExcelColumn(headerName = "建筑容量", comment = "城市允许建造的最大建筑数量")
    @Column(name = "building_capacity", columnDefinition = "INT COMMENT '城市允许建造的最大建筑数量'")
    private int buildingCapacity; // 建筑容量

    @Schema(description = "城市防御值", example = "3000")
    @ExcelColumn(headerName = "防御能力值", comment = "城市的防御能力值")
    @Column(name = "defense_value", columnDefinition = "INT COMMENT '城市的防御能力值'")
    private int defenseValue; // 城市防御能力值

    @Schema(description = "每日基础税金", example = "100")
    @ExcelColumn(headerName = "每日基础税金", comment = "每日基础税金（金币）")
    @Column(name = "daily_tax_gold", columnDefinition = "INT COMMENT '每日基础税金（金币）'")
    private int dailyTaxGold; // 每日基础税金（金币）

    @Schema(description = "每日基础税银", example = "500")
    @ExcelColumn(headerName = "每日基础税银", comment = "每日基础税银（银币）")
    @Column(name = "daily_tax_silver", columnDefinition = "INT COMMENT '每日基础税银（银币）'")
    private int dailyTaxSilver; // 每日基础税银（银币）

    @Schema(description = "城市等级，取值如下：COUNTY（县城）、PREFECTURE（郡城）、PASS（关城）、CAPITAL（都城）、METROPOLIS（首都）", example = "CAPITAL")
    @ExcelColumn(headerName = "城市等级", comment = "城市的等级类型，取值如下：COUNTY（县城）、PREFECTURE（郡城）、PASS（关城）、CAPITAL（都城）、METROPOLIS（首都）")
    @Enumerated(EnumType.STRING)
    @Column(name = "city_level", columnDefinition = "VARCHAR(20) COMMENT '城市的等级类型'")
    private CityLevelEnum cityLevel; // 城市等级（枚举类）

    @Schema(description = "城市图标URL", example = "http://example.com/icon_luoyang.png")
    @ExcelColumn(headerName = "城市图标URL", comment = "城市图标的URL")
    @Column(name = "icon_url", columnDefinition = "VARCHAR(255) COMMENT '城市图标的URL'")
    private String iconUrl; // 城市图标URL
}
