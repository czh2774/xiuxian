package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CharacterItemDTO类，用于传输角色道具实例的数据。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterItemDTO {
    private Long id;                  // 道具实例ID
    private Long characterId;         // 角色ID
    private Long itemTemplateId;      // 道具模板ID
    private int quantity;             // 道具数量
    private LocalDateTime acquiredAt; // 道具获取时间
    private ItemCategory itemCategory;// 道具类别
    private LocalDateTime lastUsedAt; // 最后一次使用时间
    private boolean isEquipped;       // 是否装备
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 最后一次更新时间

    /**
     * 将 CharacterItem 实体转换为 CharacterItemDTO
     *
     * @param characterItem CharacterItem 实体对象
     */
    public CharacterItemDTO(CharacterItem characterItem) {
        this.id = characterItem.getId();
        this.characterId = characterItem.getCharacterId();
        this.itemTemplateId = characterItem.getItemTemplateId();
        this.quantity = characterItem.getQuantity();
        this.acquiredAt = characterItem.getAcquiredAt();
        this.itemCategory = characterItem.getItemCategory();
        this.lastUsedAt = characterItem.getLastUsedAt();
        this.isEquipped = characterItem.isEquipped();
        this.createdAt = characterItem.getCreatedAt();
        this.updatedAt = characterItem.getUpdatedAt();
    }
}
