package com.xiuxian.xiuxianserver.converter;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;

public class GeneralsTemplateConverter {

    /**
     * 将 GeneralsTemplate 实体转换为 DTO。
     *
     * @param entity GeneralsTemplate 实体对象
     * @return GeneralsTemplateDTO 对象
     */
    public static GeneralsTemplateDTO toDto(GeneralsTemplate entity) {
        if (entity == null) {
            return null;
        }
        return GeneralsTemplateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rarity(entity.getRarity()) // 直接传递 RarityEnum 类型
                .initialLevel(entity.getInitialLevel())
                .initialStars(entity.getInitialStars())
                .strength(entity.getStrength())
                .intelligence(entity.getIntelligence())
                .charisma(entity.getCharisma())
                .leadership(entity.getLeadership())
                .attack(entity.getAttack())
                .defense(entity.getDefense())
                .speed(entity.getSpeed())
                .troops(entity.getTroops())
                .attackPerLevel(entity.getAttackPerLevel())
                .defensePerLevel(entity.getDefensePerLevel())
                .troopsPerLevel(entity.getTroopsPerLevel())
                .attackPerTier(entity.getAttackPerTier())
                .defensePerTier(entity.getDefensePerTier())
                .troopsPerTier(entity.getTroopsPerTier())
                .normalTalentId(entity.getNormalTalentId())
                .awakeningTalentId(entity.getAwakeningTalentId())
                .initialSkillIds(entity.getInitialSkillIds())
                .frontTroopId(entity.getFrontTroopId())
                .rearTroopId(entity.getRearTroopId())
                .appearanceTemplateId(entity.getAppearanceTemplateId())
                .description(entity.getDescription())
                .biography(entity.getBiography())
                .build();
    }

    /**
     * 将 GeneralsTemplateDTO 转换为实体 GeneralsTemplate。
     *
     * @param dto GeneralsTemplateDTO 对象
     * @return GeneralsTemplate 实体对象
     */
    public static GeneralsTemplate toEntity(GeneralsTemplateDTO dto) {
        if (dto == null) {
            return null;
        }
        return GeneralsTemplate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .rarity(dto.getRarity()) // 直接传递 RarityEnum 类型
                .initialLevel(dto.getInitialLevel())
                .initialStars(dto.getInitialStars())
                .strength(dto.getStrength())
                .intelligence(dto.getIntelligence())
                .charisma(dto.getCharisma())
                .leadership(dto.getLeadership())
                .attack(dto.getAttack())
                .defense(dto.getDefense())
                .speed(dto.getSpeed())
                .troops(dto.getTroops())
                .attackPerLevel(dto.getAttackPerLevel())
                .defensePerLevel(dto.getDefensePerLevel())
                .troopsPerLevel(dto.getTroopsPerLevel())
                .attackPerTier(dto.getAttackPerTier())
                .defensePerTier(dto.getDefensePerTier())
                .troopsPerTier(dto.getTroopsPerTier())
                .normalTalentId(dto.getNormalTalentId())
                .awakeningTalentId(dto.getAwakeningTalentId())
                .initialSkillIds(dto.getInitialSkillIds())
                .frontTroopId(dto.getFrontTroopId())
                .rearTroopId(dto.getRearTroopId())
                .appearanceTemplateId(dto.getAppearanceTemplateId())
                .description(dto.getDescription())
                .biography(dto.getBiography())
                .build();
    }
}
