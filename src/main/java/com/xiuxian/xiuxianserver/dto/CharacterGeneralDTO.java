package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CharacterGeneralDTO类，用于传输角色武将实例的数据。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterGeneralDTO {

    private Long id;  // 角色武将记录的唯一标识符

    @NotNull(message = "角色ID不能为空")
    private Long characterId; // 角色ID

    @NotNull(message = "武将模板ID不能为空")
    private Long generalTemplateId; // 武将模板ID

    private int level; // 武将当前等级
    private int stars; // 武将当前星级
    private int experience; // 武将当前经验值

    private String status; // 武将当前状态

    @NotEmpty(message = "装备的ID列表不能为空")
    private List<String> equippedItems; // 武将当前装备的ID列表

    @NotEmpty(message = "技能的ID列表不能为空")
    private List<String> currentSkills; // 武将当前技能的ID列表
}
