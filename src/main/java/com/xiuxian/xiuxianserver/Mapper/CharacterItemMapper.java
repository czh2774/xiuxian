package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.entity.CharacterItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterItemMapper {

    CharacterItemMapper INSTANCE = Mappers.getMapper(CharacterItemMapper.class);

    // 将实体转换为 DTO
    CharacterItemDTO toDTO(CharacterItem characterItem);

    // 将 DTO 转换为实体
    CharacterItem toEntity(CharacterItemDTO characterItemDTO);
}
