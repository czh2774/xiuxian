package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.GeneralsTemplateRepository;
import com.xiuxian.xiuxianserver.service.GeneralsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * GeneralsTemplateServiceImpl实现类，负责处理武将模板的具体业务逻辑。
 */
@Service
public class GeneralsTemplateServiceImpl implements GeneralsTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralsTemplateServiceImpl.class);

    @Autowired
    private GeneralsTemplateRepository generalsTemplateRepository;

    /**
     * 根据ID获取武将模板
     * @param id 武将模板ID
     * @return 武将模板的DTO对象
     */
    @Override
    public GeneralsTemplateDTO getGeneralTemplateById(Long id) {
        logger.info("Fetching GeneralsTemplate with ID: {}", id);
        GeneralsTemplate template = generalsTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with ID: " + id));
        return convertToDTO(template);
    }

    /**
     * 获取所有武将模板
     * @return 武将模板的DTO列表
     */
    @Override
    public List<GeneralsTemplateDTO> getAllGeneralTemplates() {
        logger.info("Fetching all GeneralsTemplates");
        List<GeneralsTemplate> templates = generalsTemplateRepository.findAll();
        return templates.stream().map(this::convertToDTO).toList();
    }

    /**
     * 创建一个新的武将模板
     * @param request 创建模板请求的DTO对象
     * @return 创建后的武将模板DTO对象
     */
    @Override
    public GeneralsTemplateDTO createGeneralTemplate(GeneralsTemplateCreateRequestDTO request) {
        logger.info("Creating a new GeneralsTemplate with name: {}", request.getName());
        GeneralsTemplate template = new GeneralsTemplate();
        template.setName(request.getName());
        template.setRarity(request.getRarity());
        template.setInitialLevel(request.getInitialLevel());
        template.setInitialStars(request.getInitialStars());
        template.setStrength(request.getStrength());
        template.setIntelligence(request.getIntelligence());
        template.setCharisma(request.getCharisma());
        template.setLeadership(request.getLeadership());
        template.setAttack(request.getAttack());
        template.setDefense(request.getDefense());
        template.setTroops(request.getTroops());
        template.setSpeed(request.getSpeed());
        template.setAttackPerLevel(request.getAttackPerLevel());
        template.setDefensePerLevel(request.getDefensePerLevel());
        template.setTroopsPerLevel(request.getTroopsPerLevel());
        template.setAttackPerTier(request.getAttackPerTier());
        template.setDefensePerTier(request.getDefensePerTier());
        template.setTroopsPerTier(request.getTroopsPerTier());
        template.setNormalTalentId(request.getNormalTalentId());
        template.setAwakeningTalentId(request.getAwakeningTalentId());
        template.setInitialSkillIds(request.getInitialSkillIds());
        template.setFrontTroopId(request.getFrontTroopId());
        template.setRearTroopId(request.getRearTroopId());
        template.setAppearanceTemplateId(request.getAppearanceTemplateId());
        template.setDescription(request.getDescription());
        template.setBiography(request.getBiography());

        generalsTemplateRepository.save(template);
        logger.info("GeneralsTemplate created successfully with ID: {}", template.getId());
        return convertToDTO(template);
    }

    /**
     * 更新指定ID的武将模板信息
     * @param id 武将模板ID
     * @param request 更新请求的DTO对象
     * @return 更新后的武将模板DTO对象
     */
    @Override
    public GeneralsTemplateDTO updateGeneralTemplate(Long id, GeneralsTemplateUpdateRequestDTO request) {
        logger.info("Updating GeneralsTemplate with ID: {}", id);
        GeneralsTemplate template = generalsTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with ID: " + id));

        template.setName(request.getName());
        template.setRarity(request.getRarity());
        template.setInitialLevel(request.getInitialLevel());
        template.setInitialStars(request.getInitialStars());
        template.setStrength(request.getStrength());
        template.setIntelligence(request.getIntelligence());
        template.setCharisma(request.getCharisma());
        template.setLeadership(request.getLeadership());
        template.setAttack(request.getAttack());
        template.setDefense(request.getDefense());
        template.setTroops(request.getTroops());
        template.setSpeed(request.getSpeed());
        template.setAttackPerLevel(request.getAttackPerLevel());
        template.setDefensePerLevel(request.getDefensePerLevel());
        template.setTroopsPerLevel(request.getTroopsPerLevel());
        template.setAttackPerTier(request.getAttackPerTier());
        template.setDefensePerTier(request.getDefensePerTier());
        template.setTroopsPerTier(request.getTroopsPerTier());
        template.setNormalTalentId(request.getNormalTalentId());
        template.setAwakeningTalentId(request.getAwakeningTalentId());
        template.setInitialSkillIds(request.getInitialSkillIds());
        template.setFrontTroopId(request.getFrontTroopId());
        template.setRearTroopId(request.getRearTroopId());
        template.setAppearanceTemplateId(request.getAppearanceTemplateId());
        template.setDescription(request.getDescription());
        template.setBiography(request.getBiography());

        generalsTemplateRepository.save(template);
        logger.info("GeneralsTemplate updated successfully with ID: {}", template.getId());
        return convertToDTO(template);
    }

    /**
     * 删除指定ID的武将模板
     * @param id 武将模板ID
     */
    @Override
    public void deleteGeneralTemplate(Long id) {
        logger.info("Deleting GeneralsTemplate with ID: {}", id);
        GeneralsTemplate template = generalsTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with ID: " + id));
        generalsTemplateRepository.delete(template);
        logger.info("GeneralsTemplate deleted successfully with ID: {}", id);
    }

    // DTO转换方法
    private GeneralsTemplateDTO convertToDTO(GeneralsTemplate template) {
        GeneralsTemplateDTO dto = new GeneralsTemplateDTO();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setRarity(template.getRarity());
        dto.setInitialLevel(template.getInitialLevel());
        dto.setInitialStars(template.getInitialStars());
        dto.setStrength(template.getStrength());
        dto.setIntelligence(template.getIntelligence());
        dto.setCharisma(template.getCharisma());
        dto.setLeadership(template.getLeadership());
        dto.setAttack(template.getAttack());
        dto.setDefense(template.getDefense());
        dto.setTroops(template.getTroops());
        dto.setSpeed(template.getSpeed());
        dto.setAttackPerLevel(template.getAttackPerLevel());
        dto.setDefensePerLevel(template.getDefensePerLevel());
        dto.setTroopsPerLevel(template.getTroopsPerLevel());
        dto.setAttackPerTier(template.getAttackPerTier());
        dto.setDefensePerTier(template.getDefensePerTier());
        dto.setTroopsPerTier(template.getTroopsPerTier());
        dto.setNormalTalentId(template.getNormalTalentId());
        dto.setAwakeningTalentId(template.getAwakeningTalentId());
        dto.setInitialSkillIds(template.getInitialSkillIds());
        dto.setFrontTroopId(template.getFrontTroopId());
        dto.setRearTroopId(template.getRearTroopId());
        dto.setAppearanceTemplateId(template.getAppearanceTemplateId());
        dto.setDescription(template.getDescription());
        dto.setBiography(template.getBiography());
        return dto;
    }
}
