package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.BuildingTemplateDTO;
import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingTemplateMapper {
    BuildingTemplateDTO toDTO(BuildingTemplate buildingTemplate);
    BuildingTemplate toEntity(BuildingTemplateDTO buildingTemplateDTO);
}
