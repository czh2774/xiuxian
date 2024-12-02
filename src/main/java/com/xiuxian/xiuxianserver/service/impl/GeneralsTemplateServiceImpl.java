package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.mapper.GeneralsTemplateMapper;
import com.xiuxian.xiuxianserver.repository.GeneralsTemplateRepository;
import com.xiuxian.xiuxianserver.service.GeneralsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GeneralsTemplateServiceImpl 实现类，负责处理武将模板的具体业务逻辑。
 */
@Service
public class GeneralsTemplateServiceImpl implements GeneralsTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralsTemplateServiceImpl.class);

    @Autowired
    private GeneralsTemplateRepository generalsTemplateRepository;

    @Autowired
    private GeneralsTemplateMapper generalsTemplateMapper;

    /**
     * 根据 ID 获取武将模板
     * @param id 武将模板 ID
     * @return 武将模板的 DTO 对象
     */
    @Override
    public GeneralsTemplateDTO getGeneralTemplateById(Long id) {
        logger.info("获取 ID 为 {} 的武将模板", id);
        GeneralsTemplate template = generalsTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到 ID 为 " + id + " 的武将模板"));
        return generalsTemplateMapper.toDTO(template);
    }

    /**
     * 获取所有武将模板
     * @return 武将模板的 DTO 列表
     */
    @Override
    public List<GeneralsTemplateDTO> getAllGeneralTemplates() {
        logger.info("获取所有武将模板");
        List<GeneralsTemplate> templates = generalsTemplateRepository.findAll();
        return templates.stream().map(generalsTemplateMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * 创建一个新的武将模板
     * @param request 创建模板请求的 DTO 对象
     * @return 创建后的武将模板 DTO 对象
     */
    @Override
    public GeneralsTemplateDTO createGeneralTemplate(GeneralsTemplateCreateRequestDTO request) {
        logger.info("创建新的武将模板，名称: {}", request.getName());
        GeneralsTemplate template = generalsTemplateMapper.toEntity(request);
        generalsTemplateRepository.save(template);
        logger.info("成功创建武将模板，ID: {}", template.getId());
        return generalsTemplateMapper.toDTO(template);
    }

    /**
     * 更新指定 ID 的武将模板信息
     * @param id 武将模板 ID
     * @param request 更新请求的 DTO 对象
     * @return 更新后的武将模板 DTO 对象
     */
    @Override
    public GeneralsTemplateDTO updateGeneralTemplate(Long id, GeneralsTemplateUpdateRequestDTO request) {
        logger.info("更新 ID 为 {} 的武将模板", id);
        GeneralsTemplate template = generalsTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到 ID 为 " + id + " 的武将模板"));

        // 使用映射器更新实体对象
        GeneralsTemplate updatedTemplate = generalsTemplateMapper.toEntity(request);
        updatedTemplate.setId(template.getId()); // 保留原 ID

        generalsTemplateRepository.save(updatedTemplate);
        logger.info("成功更新武将模板，ID: {}", updatedTemplate.getId());
        return generalsTemplateMapper.toDTO(updatedTemplate);
    }

    /**
     * 删除指定 ID 的武将模板
     * @param id 武将模板 ID
     */
    @Override
    public void deleteGeneralTemplate(Long id) {
        logger.info("删除 ID 为 {} 的武将模板", id);
        GeneralsTemplate template = generalsTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到 ID 为 " + id + " 的武将模板"));
        generalsTemplateRepository.delete(template);
        logger.info("成功删除武将模板，ID: {}", id);
    }
}
