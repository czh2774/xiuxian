package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.util.ExcelField;
import jakarta.persistence.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 建筑位置实体类，存储建筑的坐标和解锁状态信息
 */
@ExcelField
@Data
@Entity
@Table(name = "building_location_template")
@Schema(description = "建筑位置模板，定义建筑位置的基础信息和解锁条件")
public class BuildingLocationTemplate {

    @Id
    @Schema(description = "建筑位置ID")
    @ExcelColumn(headerName = "建筑位置ID", comment = "id")
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '建筑位置ID'")
    private Long id;  // 建筑位置ID

    @Schema(description = "X坐标")
    @ExcelColumn(headerName = "X坐标", comment = "x_coordinate")
    @Column(name = "x_coordinate", columnDefinition = "DOUBLE COMMENT 'X坐标'")
    private Double xCoordinate;  // X坐标

    @Schema(description = "Y坐标")
    @ExcelColumn(headerName = "Y坐标", comment = "y_coordinate")
    @Column(name = "y_coordinate", columnDefinition = "DOUBLE COMMENT 'Y坐标'")
    private Double yCoordinate;  // Y坐标

    @Schema(description = "建筑模板ID")
    @ExcelColumn(headerName = "建筑模板ID", comment = "building_template_id")
    @Column(name = "building_template_id", columnDefinition = "BIGINT COMMENT '建筑模板ID'")
    private Long buildingTemplateId;  // 建筑模板ID

    @Schema(description = "所需角色等级")
    @ExcelColumn(headerName = "所需角色等级", comment = "required_character_level")
    @Column(name = "required_character_level", columnDefinition = "INT COMMENT '解锁该建筑位置所需的角色等级'")
    private Integer requiredCharacterLevel;  // 解锁该建筑位置所需的角色等级
}
