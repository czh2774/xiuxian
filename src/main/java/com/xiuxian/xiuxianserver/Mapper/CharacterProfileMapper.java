package com.xiuxian.xiuxianserver.Mapper;

import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import com.xiuxian.xiuxianserver.dto.CharacterProfileUpdateDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterProfileMapper {

    CharacterProfileMapper INSTANCE = Mappers.getMapper(CharacterProfileMapper.class);

    CharacterProfileDTO toDTO(CharacterProfile characterProfile);

    CharacterProfile toEntity(CharacterProfileDTO characterProfileDTO);

    // 确保 updateEntityFromDTO 方法接受 CharacterProfileUpdateDTO 参数
    void updateEntityFromDTO(CharacterProfileUpdateDTO updateDTO, @MappingTarget CharacterProfile profile);
}
