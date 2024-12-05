package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CharacterGeneralCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterGeneralUpdateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CharacterGeneralMapper {
    
    /**
     * 创建请求DTO转实体
     */
    @Mapping(target = "id", ignore = true)
    CharacterGeneral toEntity(CharacterGeneralCreateRequestDTO dto);
    
    /**
     * 更新请求DTO转实体
     */
    CharacterGeneral toEntity(CharacterGeneralUpdateRequestDTO dto);
    
    /**
     * 实体转DTO
     */
    CharacterGeneralDTO toDTO(CharacterGeneral entity);
    
    /**
     * DTO转实体
     */
    CharacterGeneral toEntity(CharacterGeneralDTO dto);
    
    /**
     * 更新实体
     * @param dto 更新请求DTO
     * @param entity 待更新的实体
     */
    void updateEntityFromDTO(CharacterGeneralUpdateRequestDTO dto, @MappingTarget CharacterGeneral entity);
}
