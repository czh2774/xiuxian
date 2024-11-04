package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 兵种升级表，存储兵种的升级铜币消耗、道具需求、兵阶提升条件和升级效果。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "troop_upgrade")
@Schema(description = "兵种升级实体")
public class TroopUpgrade {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @ExcelColumn(headerName = "兵种升级ID", comment = "兵种升级唯一标识符")
    private Long id; // 兵种升级ID

    @Column(name = "troop_template_id", nullable = false)
    @Schema(description = "兵种模板ID", example = "1")
    @ExcelColumn(headerName = "兵种模板ID", comment = "关联的兵种模板ID")
    private Long troopTemplateId; // 兵种模板ID（不使用外键）

    @Column(name = "level", nullable = false)
    @Schema(description = "兵种等级", example = "1")
    @ExcelColumn(headerName = "兵种等级", comment = "兵种的当前等级")
    private int level; // 兵种等级

    @Column(name = "tier", nullable = false)
    @Schema(description = "兵种兵阶", example = "2")
    @ExcelColumn(headerName = "兵种兵阶", comment = "兵种的当前兵阶")
    private int tier; // 兵种兵阶

    @Column(name = "coin_cost", nullable = false)
    @Schema(description = "升级所需铜币", example = "1000")
    @ExcelColumn(headerName = "铜币消耗", comment = "升级该兵种所需的铜币数量")
    private int coinCost; // 升级所需铜币

    @Column(name = "item1_id", nullable = true)
    @Schema(description = "升级所需道具1ID", example = "101")
    @ExcelColumn(headerName = "道具1ID", comment = "升级所需的第一个道具的ID")
    private Long item1Id; // 升级所需道具1的ID

    @Column(name = "item1_quantity", nullable = true)
    @Schema(description = "道具1数量", example = "5")
    @ExcelColumn(headerName = "道具1数量", comment = "升级所需的第一个道具的数量")
    private int item1Quantity; // 道具1的数量

    @Column(name = "item2_id", nullable = true)
    @Schema(description = "升级所需道具2ID", example = "102")
    @ExcelColumn(headerName = "道具2ID", comment = "升级所需的第二个道具的ID")
    private Long item2Id; // 升级所需道具2的ID

    @Column(name = "item2_quantity", nullable = true)
    @Schema(description = "道具2数量", example = "3")
    @ExcelColumn(headerName = "道具2数量", comment = "升级所需的第二个道具的数量")
    private int item2Quantity; // 道具2的数量

}
