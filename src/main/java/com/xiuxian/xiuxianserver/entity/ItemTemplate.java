package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.enums.RarityEnum;
import com.xiuxian.xiuxianserver.enums.VisualEffectEnum;
import com.xiuxian.xiuxianserver.enums.ChestTypeEnum;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import com.xiuxian.xiuxianserver.util.ExcelField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

/**
 * 该类定义游戏中所有道具的基本信息，包括道具名称、稀有度、宝箱类型等。
 */
@ExcelField // 标注该类用于生成Excel表
@Data // Lombok 自动生成 getter/setter/toString 等方法
@Schema(description = "道具模板实体类")
@Entity // 标记为 JPA 实体类
@Table(name = "item_template") // 数据库表名
public class ItemTemplate {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '道具唯一标识'")
    @ExcelColumn(headerName = "ID", comment = "道具唯一标识")
    @Schema(description = "道具唯一标识")
    private Long id;

    @Column(name = "name", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '道具的名称'")
    @ExcelColumn(headerName = "名称", comment = "道具的名称")
    @Schema(description = "道具的名称")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "rarity", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '道具的稀有度（如普通、稀有、传奇等）'")
    @ExcelColumn(headerName = "稀有度", comment = "道具的稀有度（如普通、稀有、传奇等）")
    @Schema(description = "道具的稀有度（如普通、稀有、传奇等）")
    private RarityEnum rarity;

    @Column(name = "description", length = 500, columnDefinition = "VARCHAR(500) COMMENT '道具的详细描述'")
    @ExcelColumn(headerName = "描述", comment = "道具的详细描述")
    @Schema(description = "道具的详细描述")
    private String description;

    @Column(name = "icon_path", length = 255, columnDefinition = "VARCHAR(255) COMMENT '客户端使用的图标文件路径'")
    @ExcelColumn(headerName = "图标路径", comment = "客户端使用的图标文件路径")
    @Schema(description = "客户端使用的图标文件路径")
    private String iconPath;

    @Column(name = "obtain_method", length = 255, columnDefinition = "VARCHAR(255) COMMENT '描述该道具的获取途径，用于显示和客户端跳转（如商店等）'")
    @ExcelColumn(headerName = "获取方式", comment = "描述该道具的获取途径，用于显示和客户端跳转（如商店等）")
    @Schema(description = "道具的获取方式，用户可以通过该途径获取道具")
    private String obtainMethod;

    @Column(name = "jump_functions", length = 255, columnDefinition = "VARCHAR(255) COMMENT '使用道具时跳转的功能名称'")
    @ExcelColumn(headerName = "跳转功能", comment = "使用道具时跳转的功能名称")
    @Schema(description = "道具的跳转功能，使用道具时跳转到指定功能")
    private String jumpFunctions;

    @Column(name = "is_chest", nullable = false, columnDefinition = "BOOLEAN COMMENT '道具是否为宝箱类型'")
    @ExcelColumn(headerName = "是否宝箱", comment = "道具是否为宝箱类型")
    @Schema(description = "标识道具是否为宝箱类型")
    private boolean chest;

    @Enumerated(EnumType.STRING)
    @Column(name = "chest_type", nullable = true, columnDefinition = "VARCHAR(20) COMMENT '宝箱的生成类型（随机生成、固定生成、多选生成等）'")
    @ExcelColumn(headerName = "宝箱类型", comment = "宝箱的生成类型（随机生成、固定生成、多选生成等）")
    @Schema(description = "宝箱的生成类型（如随机生成、固定生成、多选生成等）")
    private ChestTypeEnum chestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "visual_effect", nullable = true, columnDefinition = "VARCHAR(20) COMMENT '视觉效果（如光圈、光效等），用于道具在客户端中的展示'")
    @ExcelColumn(headerName = "视觉效果", comment = "视觉效果（如光圈、光效等），用于道具在客户端中的展示")
    @Schema(description = "视觉效果（如光圈、光效等），用于道具在客户端中的展示")
    private VisualEffectEnum visualEffect;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_category", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '道具的类别（可选值包括：ITEM - 道具, SKILL_FRAGMENT - 技能碎片, ARMAMENT_FRAGMENT - 军械碎片, TREASURE_FRAGMENT - 宝物碎片, SPECIALITY - 特产）'")
    @ExcelColumn(headerName = "道具类别", comment = "道具的类别（可选值包括：ITEM - 道具, SKILL_FRAGMENT - 技能碎片, ARMAMENT_FRAGMENT - 军械碎片, TREASURE_FRAGMENT - 宝物碎片, SPECIALITY - 特产）")
    @Schema(description = "道具的类别")
    private ItemCategory itemCategory;
}
