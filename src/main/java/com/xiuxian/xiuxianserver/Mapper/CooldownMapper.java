package com.xiuxian.xiuxianserver.mapper;

import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.entity.Cooldown;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 冷却时间管理实体和DTO的映射器
 */
@Mapper
public interface CooldownMapper {

    CooldownMapper INSTANCE = Mappers.getMapper(CooldownMapper.class);

    /**
     * 将 Cooldown 实体转换为 CooldownDTO
     *
     * @param cooldown 冷却实体
     * @return 冷却DTO
     */
    @Mapping(source = "characterId", target = "characterId")
    CooldownDTO toDTO(Cooldown cooldown);

    /**
     * 将 CooldownDTO 转换为 Cooldown 实体
     *
     * @param cooldownDTO 冷却DTO
     * @return 冷却实体
     */
    @Mapping(source = "characterId", target = "characterId")
    Cooldown toEntity(CooldownDTO cooldownDTO);
}
