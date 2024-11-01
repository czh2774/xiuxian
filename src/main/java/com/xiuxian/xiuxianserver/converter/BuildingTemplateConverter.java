package com.xiuxian.xiuxianserver.converter;

import com.xiuxian.xiuxianserver.dto.BuildingTemplateDTO;
import com.xiuxian.xiuxianserver.entity.BuildingTemplate;

public class BuildingTemplateConverter {

    /**
     * 将 BuildingTemplate 实体转换为 DTO。
     *
     * @param entity BuildingTemplate 实体对象
     * @return BuildingTemplateDTO 对象
     */
    public static BuildingTemplateDTO toDto(BuildingTemplate entity) {
        if (entity == null) {
            return null;
        }
        return BuildingTemplateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .maxLevel(entity.getMaxLevel())
                .isUpgradeable(entity.isUpgradeable())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    /**
     * 将 BuildingTemplateDTO 转换为实体 BuildingTemplate。
     *
     * @param dto BuildingTemplateDTO 对象
     * @return BuildingTemplate 实体对象
     */
    public static BuildingTemplate toEntity(BuildingTemplateDTO dto) {
        if (dto == null) {
            return null;

        }
        return BuildingTemplate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .maxLevel(dto.getMaxLevel())
                .isUpgradeable(dto.isUpgradeable())
                .imageUrl(dto.getImageUrl())
                .build();
    }
}
