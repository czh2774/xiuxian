package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.ItemTemplateDTO;
import com.xiuxian.xiuxianserver.entity.ItemTemplate;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.ItemTemplateRepository;
import com.xiuxian.xiuxianserver.service.ItemTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ItemTemplateServiceImpl
 * 道具模板管理的具体实现类
 */
@Service
public class ItemTemplateServiceImpl implements ItemTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(ItemTemplateServiceImpl.class);
    private final ItemTemplateRepository itemTemplateRepository;

    @Autowired
    public ItemTemplateServiceImpl(ItemTemplateRepository itemTemplateRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
    }

    @Override
    public ItemTemplateDTO getItemTemplateById(long id) {
        logger.info("开始获取道具模板，ID: {}", id);
        ItemTemplate itemTemplate = itemTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("道具模板未找到，ID: " + id));
        logger.info("成功获取道具模板，ID: {}", id);
        return new ItemTemplateDTO(itemTemplate);
    }

    @Override
    public List<ItemTemplateDTO> getAllItemTemplates() {
        logger.info("开始获取所有道具模板");
        List<ItemTemplate> itemTemplates = itemTemplateRepository.findAll();
        logger.info("成功获取道具模板数量: {}", itemTemplates.size());
        return itemTemplates.stream().map(ItemTemplateDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemTemplateDTO createItemTemplate(ItemTemplateDTO itemTemplateDTO) {
        logger.info("开始创建道具模板，名称: {}", itemTemplateDTO.getName());
        ItemTemplate itemTemplate = new ItemTemplate();

        // 复制属性
        itemTemplate.setId(itemTemplateDTO.getId());
        itemTemplate.setName(itemTemplateDTO.getName());
        itemTemplate.setItemCategory(itemTemplateDTO.getItemCategory()); // 正确的获取方法
        itemTemplate.setRarity(itemTemplateDTO.getRarity());
        itemTemplate.setDescription(itemTemplateDTO.getDescription());
        itemTemplate.setIconPath(itemTemplateDTO.getIconPath());
        itemTemplate.setObtainMethod(itemTemplateDTO.getObtainMethod());
        itemTemplate.setJumpFunctions(itemTemplateDTO.getJumpFunctions());
        itemTemplate.setChest(itemTemplateDTO.isChest());
        itemTemplate.setChestType(itemTemplateDTO.getChestType());
        itemTemplate.setVisualEffect(itemTemplateDTO.getVisualEffect());

        ItemTemplate savedItemTemplate = itemTemplateRepository.save(itemTemplate);
        logger.info("成功创建道具模板，ID: {}", savedItemTemplate.getId());
        return new ItemTemplateDTO(savedItemTemplate);
    }


    @Override
    @Transactional
    public ItemTemplateDTO updateItemTemplate(long id, ItemTemplateDTO itemTemplateDTO) {
        logger.info("开始更新道具模板，ID: {}", id);
        ItemTemplate itemTemplate = itemTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("道具模板未找到，ID: " + id));

        // 更新属性
        itemTemplate.setName(itemTemplateDTO.getName());
        itemTemplate.setItemCategory(itemTemplateDTO.getItemCategory());
        itemTemplate.setRarity(itemTemplateDTO.getRarity());
        itemTemplate.setDescription(itemTemplateDTO.getDescription());
        itemTemplate.setIconPath(itemTemplateDTO.getIconPath());
        itemTemplate.setObtainMethod(itemTemplateDTO.getObtainMethod());
        itemTemplate.setJumpFunctions(itemTemplateDTO.getJumpFunctions());
        itemTemplate.setChest(itemTemplateDTO.isChest());
        itemTemplate.setChestType(itemTemplateDTO.getChestType());
        itemTemplate.setVisualEffect(itemTemplateDTO.getVisualEffect());

        ItemTemplate updatedItemTemplate = itemTemplateRepository.save(itemTemplate);
        logger.info("成功更新道具模板，ID: {}", id);
        return new ItemTemplateDTO(updatedItemTemplate);
    }

    @Override
    @Transactional
    public void deleteItemTemplate(long id) {
        logger.info("开始删除道具模板，ID: {}", id);
        if (!itemTemplateRepository.existsById(id)) {
            logger.error("删除失败，道具模板未找到，ID: {}", id);
            throw new ResourceNotFoundException("道具模板未找到，ID: " + id);
        }
        itemTemplateRepository.deleteById(id);
        logger.info("成功删除道具模板，ID: {}", id);
    }
}
