package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CharacterBuildingMapper {
    CharacterBuildingDTO toDTO(CharacterBuilding characterBuilding);
    CharacterBuilding toEntity(CharacterBuildingDTO characterBuildingDTO);
}
