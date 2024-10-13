package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

/**
 * 更新角色基本信息 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新角色基本信息 DTO，包含可选更新的角色属性")
public class CharacterProfileUpdateDTO {
    @NotNull(message = "Character ID cannot be null")
    @Schema(description = "角色唯一ID，必填")
    private Long characterId; // 角色唯一ID

    @Schema(description = "角色名称，可选")
    private String name; // 角色名称

    @Schema(description = "角色头像，可选")
    private String avatar; // 角色头像

    @Schema(description = "角色所属势力，可选")
    private String faction; // 角色所属势力

    // 可以根据需要添加其他可选字段
}
