package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 创建角色 DTO
 */
@Data
@Schema(description = "创建角色 DTO，包含创建角色所需的属性")
public class CharacterProfileCreateDTO {
    @Schema(description = "所属玩家ID")
    private Long playerId; // 所属玩家ID

    @Schema(description = "角色名称")
    private String name; // 角色名称

    @Schema(description = "角色所属势力ID")
    private String faction; // 角色所属势力ID
}
