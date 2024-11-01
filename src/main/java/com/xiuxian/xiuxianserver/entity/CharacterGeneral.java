package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.StringListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

/**
 * 角色武将实体类，用于记录每个角色所拥有的武将及其状态。
 */
@Data
@Entity
@Table(name = "character_generals")
public class CharacterGeneral {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "角色武将记录的唯一标识符")
    private Long id;

    @Column(name = "character_id", nullable = false)
    @Schema(description = "角色ID，用于关联角色", example = "1001")
    private Long characterId;

    @Column(name = "general_template_id", nullable = false)
    @Schema(description = "武将模板ID，用于关联武将模板", example = "2001")
    private Long generalTemplateId;

    @Column(name = "level", nullable = false)
    @Schema(description = "武将当前等级", example = "10")
    private int level;

    @Column(name = "stars", nullable = false)
    @Schema(description = "武将当前星级", example = "4")
    private int stars;

    @Column(name = "experience", nullable = false)
    @Schema(description = "武将当前经验值", example = "1500")
    private int experience;

    @Column(name = "status", nullable = false)
    @Schema(description = "武将当前状态，例如ACTIVE, INJURED, TRAINING", example = "ACTIVE")
    private String status;

    @Convert(converter = StringListConverter.class)
    @Column(name = "equipped_items")
    @Schema(description = "武将当前装备的ID列表")
    private List<String> equippedItems;

    @Convert(converter = StringListConverter.class)
    @Column(name = "current_skills")
    @Schema(description = "武将当前技能的ID列表")
    private List<String> currentSkills;

    // 无参构造函数
    public CharacterGeneral() {}

    // 带参构造函数
    public CharacterGeneral(Long id, Long characterId, Long generalTemplateId, int level, int stars, int experience,
                            String status, List<String> equippedItems, List<String> currentSkills) {
        this.id = id;
        this.characterId = characterId;
        this.generalTemplateId = generalTemplateId;
        this.level = level;
        this.stars = stars;
        this.experience = experience;
        this.status = status;
        this.equippedItems = equippedItems;
        this.currentSkills = currentSkills;
    }
}
