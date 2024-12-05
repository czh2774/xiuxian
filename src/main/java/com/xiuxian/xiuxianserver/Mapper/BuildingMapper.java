package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.dto.response.BuildingInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    @Mapping(target = "templateId", source = "buildingTemplateId")
    @Mapping(target = "status", source = "buildingStatus")
    BuildingInfo toBuildingInfo(CharacterBuilding building);
} 