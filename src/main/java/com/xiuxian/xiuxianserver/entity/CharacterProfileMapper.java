package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterProfileMapper {
    CharacterProfileMapper INSTANCE = Mappers.getMapper(CharacterProfileMapper.class);

    // 定义实体类到 DTO 的转换方法
    CharacterProfileDTO toDTO(CharacterProfile characterProfile);

    // 定义 DTO 到实体类的转换方法
    CharacterProfile toEntity(CharacterProfileDTO characterProfileDTO);
}
