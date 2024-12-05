package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.dto.CharacterGeneralUpdateRequestDTO;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.mapper.CharacterGeneralMapper;
import com.xiuxian.xiuxianserver.repository.CharacterGeneralRepository;
import com.xiuxian.xiuxianserver.service.CharacterGeneralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CharacterGeneralServiceImpl实现类，负责处理角色武将实例的具体业务逻辑。
 */
@Service
public class CharacterGeneralServiceImpl implements CharacterGeneralService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterGeneralServiceImpl.class);

    @Autowired
    private CharacterGeneralRepository characterGeneralRepository;

    private final Snowflake snowflake;
    private final CharacterGeneralMapper characterGeneralMapper;

    public CharacterGeneralServiceImpl(Snowflake snowflake, CharacterGeneralMapper characterGeneralMapper) {
        this.snowflake = snowflake;
        this.characterGeneralMapper = characterGeneralMapper;
    }

    /**
     * 根据ID获取武将数据
     *
     * @param id 武将ID
     * @return 武将数据传输对象
     */
    @Override
    public CharacterGeneralDTO getCharacterGeneralById(Long id) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到武将，ID：" + id));
        logger.info("成功获取武将信息，ID：{}", id);
        return characterGeneralMapper.toDTO(general);
    }

    /**
     * 根据角色ID获取武将列表
     *
     * @param characterId 角色ID
     * @return 武将数据传输对象列表
     */
    @Override
    public List<CharacterGeneralDTO> getCharacterGeneralsByCharacterId(Long characterId) {
        List<CharacterGeneral> generals = characterGeneralRepository.findByCharacterId(characterId);
        logger.info("获取角色ID为 {} 的武将列表，共 {} 个武将", characterId, generals.size());
        return generals.stream().map(characterGeneralMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * 创建新的武将
     *
     * @param request 武将请求数据传输对象
     * @return 创建的武将数据传输对象
     */
    @Override
    public CharacterGeneralDTO createCharacterGeneral(CharacterGeneralDTO request) {
        Long id = snowflake.nextId();
        CharacterGeneral general = characterGeneralMapper.toEntity(request);
        general.setId(id);
        
        characterGeneralRepository.save(general);
        logger.info("创建新武将成功，ID：{}", id);
        return characterGeneralMapper.toDTO(general);
    }

    /**
     * 更新武将数据
     *
     * @param id      武将ID
     * @param request 武将请求数据传输对象
     * @return 更新后的武将数据传输对象
     */
    @Override
    public CharacterGeneralDTO updateCharacterGeneral(Long id, CharacterGeneralUpdateRequestDTO request) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到武将，ID：" + id));

        characterGeneralMapper.updateEntityFromDTO(request, general);
        characterGeneralRepository.save(general);
        logger.info("更新武将成功，ID：{}", id);
        return characterGeneralMapper.toDTO(general);
    }

    /**
     * 删除武将
     *
     * @param id 武将ID
     */
    @Override
    public void deleteCharacterGeneral(Long id) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到武将，ID：" + id));
        characterGeneralRepository.delete(general);
        logger.info("删除武将成功，ID：{}", id);
    }


}
