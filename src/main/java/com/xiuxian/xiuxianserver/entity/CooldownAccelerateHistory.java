package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;
import java.time.LocalDateTime;

@Entity
@Data
public class CooldownAccelerateHistory {
    @Id
    private Long id;
    private Long characterId;
    private Long cooldownId;
    private AccelerateItemType itemType;
    private int itemCount;
    private int accelerationTime;
    private LocalDateTime accelerateTime;

    public CooldownAccelerateHistory(Long characterId, Long cooldownId, AccelerateItemType itemType, 
            int itemCount, int accelerationTime) {
        this.characterId = characterId;
        this.cooldownId = cooldownId;
        this.itemType = itemType;
        this.itemCount = itemCount;
        this.accelerationTime = accelerationTime;
        this.accelerateTime = LocalDateTime.now();
    }
} 