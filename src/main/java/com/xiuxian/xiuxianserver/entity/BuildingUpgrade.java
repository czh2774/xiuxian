package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 建筑升级表，存储建筑每一级的升级时间、资源消耗、升级条件和效果。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "building_upgrade")
@Schema(description = "建筑升级实体")
public class BuildingUpgrade {

    @Id
    @ExcelColumn(headerName = "建筑升级ID", comment = "建筑升级唯一标识符")
    private Long id; // 建筑升级ID

    @Schema(description = "建筑模板ID", example = "1")
    @ExcelColumn(headerName = "建筑模板ID", comment = "关联的建筑模板ID")
    private Long buildingTemplateId; // 建筑模板ID（不使用外键）

    @Schema(description = "建筑等级", example = "1")
    @ExcelColumn(headerName = "建筑等级", comment = "建筑的当前升级等级")
    private int level; // 建筑等级

    @Schema(description = "升级时间", example = "1800")
    @ExcelColumn(headerName = "升级时间", comment = "升级到该等级所需的时间，单位为秒")
    private int upgradeTime; // 升级时间

    // 修改后的资源消耗字段
    @Schema(description = "消耗的粮食数量", example = "500")
    @ExcelColumn(headerName = "消耗粮食", comment = "升级该建筑所需的粮食")
    private int foodCost; // 消耗粮食

    @Schema(description = "消耗的木材数量", example = "300")
    @ExcelColumn(headerName = "消耗木材", comment = "升级该建筑所需的木材")
    private int woodCost; // 消耗木材

    @Schema(description = "消耗的铁矿数量", example = "200")
    @ExcelColumn(headerName = "消耗铁矿", comment = "升级该建筑所需的铁矿")
    private int ironCost; // 消耗铁矿



    @Schema(description = "升级条件", example = "[{required_buildings:[{building_id:2,level:5}],player_level:10},{required_buildings:[{building_id:4,level:5}],player_level:10}]")
    @ExcelColumn(headerName = "升级条件", comment = "升级的前置条件，JSON格式")
    private String upgradeRequirements; // 升级条件（JSON格式）
}
