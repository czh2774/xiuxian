package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.entity.CharacterItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CharacterItemMapper {
    CharacterItemDTO toDTO(CharacterItem characterItem);

    CharacterItem toEntity(CharacterItemDTO characterItemDTO);
}