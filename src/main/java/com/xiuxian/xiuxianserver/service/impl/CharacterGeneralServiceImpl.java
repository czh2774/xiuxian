package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterGeneralRepository;
import com.xiuxian.xiuxianserver.service.CharacterGeneralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    public CharacterGeneralServiceImpl(Snowflake snowflake) {
        this.snowflake = snowflake;
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
        return convertToDTO(general);
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
        return generals.stream().map(this::convertToDTO).collect(Collectors.toList());
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
        CharacterGeneral general = new CharacterGeneral(id, request.getCharacterId(), request.getGeneralTemplateId(),
                request.getLevel(), request.getStars(), request.getExperience(), request.getStatus(),
                request.getEquippedItems(), request.getCurrentSkills());

        characterGeneralRepository.save(general);
        logger.info("创建新武将成功，ID：{}", id);
        return convertToDTO(general);
    }

    /**
     * 更新武将数据
     *
     * @param id      武将ID
     * @param request 武将请求数据传输对象
     * @return 更新后的武将数据传输对象
     */
    @Override
    public CharacterGeneralDTO updateCharacterGeneral(Long id, CharacterGeneralDTO request) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到武将，ID：" + id));

        general.setLevel(request.getLevel());
        general.setStars(request.getStars());
        general.setExperience(request.getExperience());
        general.setStatus(request.getStatus());
        general.setEquippedItems(request.getEquippedItems());
        general.setCurrentSkills(request.getCurrentSkills());

        characterGeneralRepository.save(general);
        logger.info("更新武将成功，ID：{}", id);
        return convertToDTO(general);
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

    /**
     * 初始化默认的武将列表
     *
     * @param characterId 角色ID
     * @return 初始化后的武将列表
     */
    @Override
    public List<CharacterGeneral> initializeDefaultGenerals(Long characterId) {
        List<CharacterGeneral> defaultGenerals = new ArrayList<>();

        // 示例武将1
        CharacterGeneral general1 = new CharacterGeneral();
        general1.setId(snowflake.nextId());  // 使用雪花ID生成
        general1.setCharacterId(characterId);
        general1.setGeneralTemplateId(1L); // 模板ID
        general1.setLevel(1);
        general1.setStars(3);
        general1.setExperience(0);
        general1.setStatus("Active");
        defaultGenerals.add(general1);

        // 示例武将2
        CharacterGeneral general2 = new CharacterGeneral();
        general2.setId(snowflake.nextId());  // 使用雪花ID生成
        general2.setCharacterId(characterId);
        general2.setGeneralTemplateId(2L); // 模板ID
        general2.setLevel(1);
        general2.setStars(3);
        general2.setExperience(0);
        general2.setStatus("Active");
        defaultGenerals.add(general2);

        characterGeneralRepository.saveAll(defaultGenerals);
        logger.info("初始化 {} 的默认武将列表，角色ID：{}", defaultGenerals.size(), characterId);
        return defaultGenerals;
    }

    /**
     * DTO转换方法
     */
    private CharacterGeneralDTO convertToDTO(CharacterGeneral general) {
        CharacterGeneralDTO dto = new CharacterGeneralDTO();
        dto.setId(general.getId());
        dto.setCharacterId(general.getCharacterId());
        dto.setGeneralTemplateId(general.getGeneralTemplateId());
        dto.setLevel(general.getLevel());
        dto.setStars(general.getStars());
        dto.setExperience(general.getExperience());
        dto.setStatus(general.getStatus());
        dto.setEquippedItems(general.getEquippedItems());
        dto.setCurrentSkills(general.getCurrentSkills());
        return dto;
    }
}
