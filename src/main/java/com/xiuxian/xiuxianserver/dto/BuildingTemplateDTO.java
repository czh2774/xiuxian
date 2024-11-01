package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import lombok.Builder;

/**
 * BuildingTemplateDTO
 * 封装建筑模板的基本信息
 */
@Data
@Builder
public class BuildingTemplateDTO {

    private Long id; // 与实体类字段名称一致
    private String name;
    private String description;
    private int maxLevel;
    private boolean isUpgradeable;
    private String imageUrl;

    // 使用 @Data 和 @Builder 注解，无需手动编写构造函数和 getter/setter 方法
}
