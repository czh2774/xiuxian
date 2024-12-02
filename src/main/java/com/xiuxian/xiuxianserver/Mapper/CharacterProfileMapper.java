package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import com.xiuxian.xiuxianserver.dto.CharacterProfileUpdateDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterProfileMapper {

    CharacterProfileMapper INSTANCE = Mappers.getMapper(CharacterProfileMapper.class);

    // 将 CharacterProfile 实体转换为 CharacterProfileDTO
    CharacterProfileDTO toDTO(CharacterProfile characterProfile);

    // 将 CharacterProfileDTO 转换为 CharacterProfile 实体
    CharacterProfile toEntity(CharacterProfileDTO characterProfileDTO);

    // 从 CharacterProfileUpdateDTO 更新 CharacterProfile 实体
    @Mapping(target = "id", ignore = true)  // 忽略不可更改的字段，例如 id
    @Mapping(target = "createdAt", ignore = true)  // 忽略其他不可更新字段
    void updateEntityFromDTO(CharacterProfileUpdateDTO updateDTO, @MappingTarget CharacterProfile profile);
}
