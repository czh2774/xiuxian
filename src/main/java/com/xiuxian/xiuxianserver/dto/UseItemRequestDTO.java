package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * UseItemRequestDTO类，用于处理使用道具的请求数据
 */
public class UseItemRequestDTO {

    @NotNull
    @Schema(description = "道具实例ID", example = "1001")
    private Long itemId; // 道具实例ID

    @Min(1)
    @Schema(description = "使用数量", example = "1")
    private int quantity; // 使用数量

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
