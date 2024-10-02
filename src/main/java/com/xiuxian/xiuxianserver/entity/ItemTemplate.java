package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.annotations.ExcelColumn;
import com.xiuxian.xiuxianserver.enums.RarityEnum;
import com.xiuxian.xiuxianserver.enums.VisualEffectEnum;
import com.xiuxian.xiuxianserver.enums.ChestTypeEnum;
import com.xiuxian.xiuxianserver.util.ExcelField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 该类定义游戏中所有道具的基本信息，包括道具名称、稀有度、宝箱类型等。
 */
@ExcelField     // 标注该类用于生成Excel表
@Data // Lombok 自动生成 getter/setter/toString 等方法
@Schema(description = "道具模板实体类")
public class ItemTemplate {

    @ExcelColumn(headerName = "ID", comment = "道具唯一标识")
    @Schema(description = "道具唯一标识")
    private Long id;

    @ExcelColumn(headerName = "名称", comment = "道具的名称")
    @Schema(description = "道具的名称")
    private String name;

    @ExcelColumn(headerName = "类型", comment = "道具类型（如武器、消耗品、材料等）")
    @Schema(description = "道具的类型（如武器、消耗品、材料等）")
    private String type;

    @ExcelColumn(headerName = "稀有度", comment = "道具的稀有度（如普通、稀有、传奇等）")
    @Schema(description = "道具的稀有度（如普通、稀有、传奇等）")
    private RarityEnum rarity;

    @ExcelColumn(headerName = "描述", comment = "道具的详细描述")
    @Schema(description = "道具的详细描述")
    private String description;

    @ExcelColumn(headerName = "图标路径", comment = "客户端使用的图标文件路径")
    @Schema(description = "客户端使用的图标文件路径")
    private String iconPath;

    @ExcelColumn(headerName = "获取方式", comment = "描述该道具的获取途径，用于显示和客户端跳转（如商店等）")
    @Schema(description = "道具的获取方式，用户可以通过该途径获取道具")
    private String obtainMethod;

    @ExcelColumn(headerName = "跳转功能", comment = "使用道具时跳转的功能名称")
    @Schema(description = "道具的跳转功能，使用道具时跳转到指定功能")
    private String jumpFunctions;

    @ExcelColumn(headerName = "是否宝箱", comment = "道具是否为宝箱类型")
    @Schema(description = "标识道具是否为宝箱类型")
    private boolean isChest;

    @ExcelColumn(headerName = "宝箱类型", comment = "宝箱的生成类型（随机生成、固定生成、多选生成等）")
    @Schema(description = "宝箱的生成类型（如随机生成、固定生成、多选生成等）")
    private ChestTypeEnum chestType;

    @ExcelColumn(headerName = "视觉效果", comment = "视觉效果（如光圈、光效等），用于道具在客户端中的展示")
    @Schema(description = "视觉效果（如光圈、光效等），用于道具在客户端中的展示")
    private VisualEffectEnum visualEffect;
}
