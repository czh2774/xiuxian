package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 建筑效果模板表，存储每种建筑的效果类型、基础值和成长系数。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "building_effect_template", indexes = {
        @Index(name = "idx_template_id", columnList = "template_id")
})
@Schema(description = "建筑效果模板实体")
public class BuildingEffectTemplate {

    @Id
    @ExcelColumn(headerName = "效果ID", comment = "效果唯一标识符")
    private Long effectId; // 效果ID

    @Schema(description = "建筑模板ID", example = "1")
    @ExcelColumn(headerName = "建筑模板ID", comment = "关联的建筑模板ID")
    @Column(name = "template_id")
    private Long templateId; // 建筑模板ID（不使用外键）

    @Schema(description = "效果类型", example = "INCREASE_PRODUCTION")
    @ExcelColumn(headerName = "效果类型", comment = "效果的类型，例如增加生产、增加存储")
    @Column(name = "effect_type", length = 50)
    private String effectType; // 效果类型（可以使用枚举）

    @Schema(description = "基础值", example = "500")
    @ExcelColumn(headerName = "基础值", comment = "效果的基础值")
    @Column(name = "base_value")
    private int baseValue; // 基础值

    @Schema(description = "成长系数", example = "50")
    @ExcelColumn(headerName = "成长系数", comment = "每级的成长系数")
    @Column(name = "growth_coefficient")
    private int growthCoefficient; // 成长系数

    @Schema(description = "每10级的倍数递增", example = "1.5")
    @ExcelColumn(headerName = "递增倍数", comment = "每10级递增系数倍数")
    @Column(name = "growth_multiplier")
    private double growthMultiplier; // 每10级递增系数倍数

    /**
     * 计算当前等级的效果值
     *
     * @param level 当前等级
     * @return 计算后的效果值
     */
    public int calculateEffectValue(int level) {
        int multiplierCount = (level - 1) / 10; // 每10级应用一次倍数
        double adjustedGrowthCoefficient = growthCoefficient * Math.pow(growthMultiplier, multiplierCount);
        return (int)(baseValue + (level - 1) * adjustedGrowthCoefficient);
    }

    // 重写 toString 方法便于输出
    @Override
    public String toString() {
        return "BuildingEffectTemplate{" +
                "effectId=" + effectId +
                ", templateId=" + templateId +
                ", effectType='" + effectType + '\'' +
                ", baseValue=" + baseValue +
                ", growthCoefficient=" + growthCoefficient +
                ", growthMultiplier=" + growthMultiplier +
                '}';
    }
}
