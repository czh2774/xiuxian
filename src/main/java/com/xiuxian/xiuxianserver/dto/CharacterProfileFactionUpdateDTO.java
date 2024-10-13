package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色势力更新信息传输对象，用于更新角色的势力")
public class CharacterProfileFactionUpdateDTO {

    @Schema(description = "角色唯一ID", example = "1", required = true)
    private Long characterId; // 角色唯一ID

    @Schema(description = "角色所属势力", example = "势力A", required = true)
    private String faction; // 角色所属势力
}
