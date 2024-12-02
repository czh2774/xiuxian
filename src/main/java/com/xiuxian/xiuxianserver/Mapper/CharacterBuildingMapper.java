package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterBuildingMapper {

    CharacterBuildingMapper INSTANCE = Mappers.getMapper(CharacterBuildingMapper.class);

    // 实体到 DTO 的映射
    @Mapping(source = "id", target = "characterBuildingId")
    CharacterBuildingDTO toDTO(CharacterBuilding entity);

    // DTO 到实体的映射（用于创建时）
    @Mapping(target = "id", ignore = true)  // ID 由 Snowflake 或数据库生成
    @Mapping(target = "currentLevel", constant = "1")  // 初始等级设为 1
    @Mapping(target = "buildingStatus", constant = "IDLE")  // 初始状态为 IDLE
    CharacterBuilding toEntity(CharacterBuildingCreateRequestDTO dto);
}
