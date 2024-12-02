package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CharacterGeneralMapper {
    CharacterGeneralMapper INSTANCE = Mappers.getMapper(CharacterGeneralMapper.class);

    CharacterGeneralDTO toDTO(CharacterGeneral characterGeneral);

    CharacterGeneral toEntity(CharacterGeneralDTO characterGeneralDTO);
}
