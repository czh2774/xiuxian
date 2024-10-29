package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 兵种升级表，存储兵种的升级时间、资源消耗、兵阶提升条件和升级效果。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "troop_upgrade")
@Schema(description = "兵种升级实体")
public class TroopUpgrade {

    @Id
    @ExcelColumn(headerName = "兵种升级ID", comment = "兵种升级唯一标识符")
    private Long id; // 兵种升级ID

    @Schema(description = "兵种模板ID", example = "1")
    @ExcelColumn(headerName = "兵种模板ID", comment = "关联的兵种模板ID")
    private Long troopTemplateId; // 兵种模板ID（不使用外键）

    @Schema(description = "兵种等级", example = "1")
    @ExcelColumn(headerName = "兵种等级", comment = "兵种的当前等级")
    private int level; // 兵种等级

    @Schema(description = "兵种兵阶", example = "2")
    @ExcelColumn(headerName = "兵种兵阶", comment = "兵种的当前兵阶")
    private int tier; // 兵种兵阶

    @Schema(description = "升级时间", example = "1800")
    @ExcelColumn(headerName = "升级时间", comment = "升级到该等级所需的时间，单位为秒")
    private int upgradeTime; // 升级时间

    @Schema(description = "资源消耗", example = "{\"wood\": 500, \"iron\": 300}")
    @ExcelColumn(headerName = "资源消耗", comment = "升级该兵种所需的资源，JSON格式")
    private String resourceCost; // 资源消耗（JSON格式）

    @Schema(description = "兵种效果", example = "{\"attack_bonus\": {\"attack\": 10}}")
    @ExcelColumn(headerName = "兵种效果", comment = "升级后的兵种效果，JSON格式")
    private String effect; // 兵种效果（JSON格式）

    @Schema(description = "升级条件", example = "{\"required_general_level\": 10}")
    @ExcelColumn(headerName = "升级条件", comment = "升级的前置条件，JSON格式")
    private String upgradeRequirements; // 升级条件（JSON格式）
}
