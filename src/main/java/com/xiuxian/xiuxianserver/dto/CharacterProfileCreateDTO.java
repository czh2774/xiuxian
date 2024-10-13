package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CharacterProfileCreateDTO
 * 用于创建新角色档案的DTO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色档案创建DTO")
public class CharacterProfileCreateDTO {
    @Schema(description = "角色Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VySWQiLCJleHAiOjE2Mjg3NzUyNDQsImlhdCI6MTYyODc3MTY0NH0.XU6n89QVceCJH0QsSY8JgUSQYBO5E3UrTZa4dX09MK8\n")
    private String token;

    @Schema(description = "角色名", example = "Hero")
    private String name;


    @Schema(description = "角色派系ID", example = "2001")
    private String faction;
}
