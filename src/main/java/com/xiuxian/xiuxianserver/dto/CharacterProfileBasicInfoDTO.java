package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色基本信息 DTO
 */
@Data
@Schema(description = "角色基本信息 DTO，包含角色的基本属性")
public class CharacterProfileBasicInfoDTO {
    @Schema(description = "角色唯一ID")
    private Long characterId; // 角色唯一ID

    @Schema(description = "角色名称")
    private String name; // 角色名称

    @Schema(description = "角色头像")
    private String avatar; // 角色头像

    @Schema(description = "角色所属势力")
    private String faction; // 角色所属势力
}
