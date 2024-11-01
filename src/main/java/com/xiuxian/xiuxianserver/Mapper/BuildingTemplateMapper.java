package com.xiuxian.xiuxianserver.Mapper;

import com.xiuxian.xiuxianserver.dto.BuildingTemplateDTO;
import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingTemplateMapper {
    // 实体到 DTO 的映射
    BuildingTemplateDTO toDTO(BuildingTemplate buildingTemplate);

    // DTO 到实体的映射
    BuildingTemplate toEntity(BuildingTemplateDTO buildingTemplateDTO);
}
