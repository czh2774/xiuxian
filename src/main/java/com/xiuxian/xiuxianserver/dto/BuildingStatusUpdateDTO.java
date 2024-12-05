package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingStatusUpdateDTO {
    @NotNull(message = "建筑ID不能为空")
    private Long buildingId;
    
    @NotNull(message = "队列ID不能为空")
    private Integer queueId = 1;  // 默认队列
}
