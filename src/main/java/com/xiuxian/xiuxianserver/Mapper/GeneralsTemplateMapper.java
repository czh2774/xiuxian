package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GeneralsTemplateMapper {
    @Mapping(target = "id", ignore = true)
    GeneralsTemplate toEntity(GeneralsTemplateCreateRequestDTO dto);
    
    GeneralsTemplate toEntity(GeneralsTemplateUpdateRequestDTO dto);
    GeneralsTemplateDTO toDTO(GeneralsTemplate entity);
    GeneralsTemplate toEntity(GeneralsTemplateDTO dto);
}
