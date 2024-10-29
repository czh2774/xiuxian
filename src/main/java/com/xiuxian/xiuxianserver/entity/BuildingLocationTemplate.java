package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.util.ExcelField;
import jakarta.persistence.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@ExcelField
@Data
@Entity
@Table(name = "building_location_template")
@Schema(description = "建筑位置实体类，存储建筑的坐标和解锁状态信息")
public class BuildingLocationTemplate {

    @Id
    @Schema(description = "建筑位置ID")
    @ExcelColumn(headerName = "建筑位置ID", comment = "id")
    private Long id;  // 建筑位置ID

    @Schema(description = "X坐标")
    @ExcelColumn(headerName = "X坐标", comment = "x_coordinate")
    private Double xCoordinate;  // X坐标

    @Schema(description = "Y坐标")
    @ExcelColumn(headerName = "Y坐标", comment = "y_coordinate")
    private Double yCoordinate;  // Y坐标

    @Schema(description = "建筑模板ID")
    @ExcelColumn(headerName = "建筑模板ID", comment = "building_template_id")
    private Long buildingTemplateId;  // 建筑模板ID

    @Schema(description = "位置是否解锁")
    @ExcelColumn(headerName = "位置是否解锁", comment = "is_unlocked")
    private Boolean isUnlocked;  // 是否解锁
}
