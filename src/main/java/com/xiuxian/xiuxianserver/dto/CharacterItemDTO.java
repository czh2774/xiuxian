package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CharacterItemDTO {
    private Long id;
    private Long characterId;
    private Long itemTemplateId;
    private int quantity;
    private LocalDateTime acquiredAt;
    private LocalDateTime lastUsedAt;
    private boolean isEquipped;
    private String itemCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
