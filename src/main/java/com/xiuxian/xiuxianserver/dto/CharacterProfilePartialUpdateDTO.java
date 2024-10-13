package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色部分更新信息传输对象，用于更新角色的部分信息")
public class CharacterProfilePartialUpdateDTO {

    @Schema(description = "角色唯一ID", example = "1", required = true)
    private Long characterId; // 角色唯一ID

    @Schema(description = "角色名称", example = "新角色名称")
    private String name; // 角色名称

    @Schema(description = "角色头像信息", example = "http://example.com/avatar.png")
    private String avatar; // 角色头像信息


    // 其他需要更新的字段可以继续添加
}
