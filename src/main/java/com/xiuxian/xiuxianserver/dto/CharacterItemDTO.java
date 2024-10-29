package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.enums.ItemCategory;
import java.time.LocalDateTime;

/**
 * CharacterItemDTO类，用于传输角色道具实例的数据。
 */
public class CharacterItemDTO {
    private Long id;        // 道具实例ID
    private Long characterId;           // 角色ID
    private Long itemTemplateId;        // 道具模板ID
    private int quantity;                 // 道具数量
    private LocalDateTime acquiredAt;     // 道具获取时间
    private ItemCategory itemCategory;    // 道具类别
    private LocalDateTime lastUsedAt;     // 最后一次使用时间
    private boolean isEquipped;           // 是否装备
    private LocalDateTime createdAt;      // 创建时间
    private LocalDateTime updatedAt;      // 最后一次更新时间

    // Getters and Setters
    public Long getItemInstanceId() {
        return id;
    }

    public void setItemInstanceId(Long itemInstanceId) {
        this.id = itemInstanceId;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public Long getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(Long itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAcquiredAt() {
        return acquiredAt;
    }

    public void setAcquiredAt(LocalDateTime acquiredAt) {
        this.acquiredAt = acquiredAt;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public LocalDateTime getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(LocalDateTime lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
