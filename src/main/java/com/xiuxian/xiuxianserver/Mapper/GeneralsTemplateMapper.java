package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GeneralsTemplateMapper {
    
    /**
     * 创建请求DTO转实体
     */
    @Mapping(target = "id", ignore = true)
    GeneralsTemplate toEntity(GeneralsTemplateCreateRequestDTO dto);
    
    /**
     * 更新请求DTO转实体
     */
    GeneralsTemplate toEntity(GeneralsTemplateUpdateRequestDTO dto);
    
    /**
     * 实体转DTO
     */
    GeneralsTemplateDTO toDTO(GeneralsTemplate entity);
    
    /**
     * DTO转实体
     */
    GeneralsTemplate toEntity(GeneralsTemplateDTO dto);
    
    /**
     * 更新实体
     * @param dto 更新请求DTO
     * @param entity 待更新的实体
     */
    void updateEntityFromDTO(GeneralsTemplateUpdateRequestDTO dto, @MappingTarget GeneralsTemplate entity);
}
