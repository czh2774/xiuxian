package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.ItemTemplateDTO;
import com.xiuxian.xiuxianserver.entity.ItemTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemTemplateMapper {

    ItemTemplateMapper INSTANCE = Mappers.getMapper(ItemTemplateMapper.class);

    ItemTemplateDTO toDTO(ItemTemplate itemTemplate);

    ItemTemplate toEntity(ItemTemplateDTO itemTemplateDTO);

    // 这里定义更新方法，将 DTO 的字段更新到已有的实体中
    void updateEntityFromDTO(ItemTemplateDTO itemTemplateDTO, @MappingTarget ItemTemplate itemTemplate);
}
