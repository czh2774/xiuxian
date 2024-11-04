package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * BuildingTemplateDTO
 * 封装建筑模板的基本信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildingTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private boolean isUpgradeable;
    private String imageUrl;
}
