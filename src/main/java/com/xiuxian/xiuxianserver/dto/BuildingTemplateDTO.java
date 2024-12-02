package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "建筑模板DTO")
public class BuildingTemplateDTO {
    @Schema(description = "建筑模板唯一标识符")
    private Long id;

    @Schema(description = "建筑名称", example = "城墙")
    private String name;

    @Schema(description = "建筑描述", example = "城墙用于防御敌人的进攻")
    private String description;

    @Schema(description = "是否可升级", example = "true")
    private boolean upgradeable;

    @Schema(description = "建筑图标URL", example = "http://example.com/image.png")
    private String imageUrl;
}
