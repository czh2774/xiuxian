package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import com.xiuxian.xiuxianserver.dto.CharacterProfileUpdateDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.dto.response.CharacterInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CharacterProfileMapper {
    // 实体转DTO
    @Mapping(target = "playerId", ignore = true)
    @Mapping(target = "maxBuildingUpgradeQueues", ignore = true)
    @Mapping(target = "currentBuildingUpgradeQueues", ignore = true)
    CharacterProfileDTO toDTO(CharacterProfile characterProfile);

    // DTO转实体
    CharacterProfile toEntity(CharacterProfileDTO characterProfileDTO);

    // 更新实体
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "playerId", ignore = true)
    void updateEntityFromDTO(CharacterProfileUpdateDTO updateDTO, @MappingTarget CharacterProfile profile);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "level", source = "level")
    @Mapping(target = "maxPopulation", ignore = true)
    @Mapping(target = "maxStorage", ignore = true)
    CharacterInfo toCharacterInfo(CharacterProfile character);
}
