package com.xiuxian.xiuxianserver.dto;

import lombok.Data;

/**
 * DTO 用于获取角色信息时请求角色ID
 */
@Data
public class CharacterIdRequestDTO {
    private Long characterId; // 角色ID
}
