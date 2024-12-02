package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneralsTemplateMapper {

    // 创建请求DTO -> 实体
    GeneralsTemplate toEntity(GeneralsTemplateCreateRequestDTO dto);

    // 更新请求DTO -> 实体
    GeneralsTemplate toEntity(GeneralsTemplateUpdateRequestDTO dto);

    // 实体 -> DTO
    GeneralsTemplateDTO toDTO(GeneralsTemplate entity);

    // DTO -> 实体（如需要在传输对象之间转换）
    GeneralsTemplate toEntity(GeneralsTemplateDTO dto);
}
