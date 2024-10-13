package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CharacterIdRequestDTO
 * 用于封装包含角色ID的请求数据传输对象。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色ID请求DTO")
public class CharacterIdRequestDTO {

    @Schema(description = "角色ID", example = "1001")
    private Long characterId;
}
