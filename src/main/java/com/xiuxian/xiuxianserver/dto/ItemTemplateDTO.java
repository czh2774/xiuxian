package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.ItemTemplate;
import com.xiuxian.xiuxianserver.enums.RarityEnum;
import com.xiuxian.xiuxianserver.enums.VisualEffectEnum;
import com.xiuxian.xiuxianserver.enums.ChestTypeEnum;
import com.xiuxian.xiuxianserver.enums.ItemCategory; // 导入 ItemCategory 枚举
import lombok.Data;

@Data
public class ItemTemplateDTO {
    private Long id;                    // 道具唯一标识
    private String name;                // 道具名称
    private RarityEnum rarity;          // 道具稀有度
    private String description;         // 道具描述
    private String iconPath;            // 图标路径
    private String obtainMethod;        // 获取方式
    private String jumpFunctions;       // 跳转功能
    private boolean isChest;            // 是否为宝箱
    private ChestTypeEnum chestType;    // 宝箱类型
    private VisualEffectEnum visualEffect; // 视觉效果
    private ItemCategory itemCategory;  // 道具类别

    // 默认构造函数
    public ItemTemplateDTO() {}

    // 通过 ItemTemplate 实例构造 DTO
    public ItemTemplateDTO(ItemTemplate itemTemplate) {
        this.id = itemTemplate.getId();
        this.name = itemTemplate.getName();
        this.rarity = itemTemplate.getRarity();
        this.description = itemTemplate.getDescription();
        this.iconPath = itemTemplate.getIconPath();
        this.obtainMethod = itemTemplate.getObtainMethod();
        this.jumpFunctions = itemTemplate.getJumpFunctions();
        this.isChest = itemTemplate.isChest();
        this.chestType = itemTemplate.getChestType();
        this.visualEffect = itemTemplate.getVisualEffect();
        this.itemCategory = itemTemplate.getItemCategory(); // 设置道具类别
    }
}
