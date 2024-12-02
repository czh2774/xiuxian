package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.xiuxian.xiuxianserver.enums.CooldownType;

@Data
public class CooldownCheckRequest {
    @NotNull(message = "角色ID不能为空")
    private Long characterId;
    
    @NotNull(message = "冷却类型不能为空")
    private CooldownType cooldownType;
    
    @NotNull(message = "目标ID不能为空")
    private Long targetId;
    
    @NotNull(message = "队列ID不能为空")
    private Integer queueId;
} 