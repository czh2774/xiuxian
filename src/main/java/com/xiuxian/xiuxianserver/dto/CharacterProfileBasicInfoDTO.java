package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CharacterProfileBasicInfoDTO
 * 传输角色基本信息的DTO类，包含角色ID、角色名和角色等级。
 */
@Data
@NoArgsConstructor  // 无参构造函数
@AllArgsConstructor // 全参构造函数
@Schema(description = "角色基本信息DTO")
public class CharacterProfileBasicInfoDTO {

    @Schema(description = "角色的唯一标识符", example = "1001")
    private Long characterId;

    @Schema(description = "角色名称", example = "Hero")
    private String name;

    @Schema(description = "角色等级", example = "10")
    private int level;

    // 构造函数：从 CharacterProfile 实体中构建 DTO
    public CharacterProfileBasicInfoDTO(CharacterProfile profile) {
        this.characterId = profile.getCharacterId();  // 调用 getCharacterId()
        this.name = profile.getName();
        this.level = profile.getLevel();
    }
}
