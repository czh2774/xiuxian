package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.GeneralsUpgradeDTO;
import com.xiuxian.xiuxianserver.entity.GeneralsUpgrade;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.GeneralsUpgradeRepository;
import com.xiuxian.xiuxianserver.service.GeneralsUpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GeneralsUpgradeServiceImpl实现类，负责处理武将升级的具体业务逻辑。
 */
@Service
public class GeneralsUpgradeServiceImpl implements GeneralsUpgradeService {

    @Autowired
    private GeneralsUpgradeRepository generalsUpgradeRepository;

    @Override
    public GeneralsUpgradeDTO getGeneralsUpgradeById(Long id) {
        GeneralsUpgrade upgrade = generalsUpgradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeneralsUpgrade not found with ID: " + id));
        return convertToDTO(upgrade);
    }

    @Override
    public List<GeneralsUpgradeDTO> getAllGeneralsUpgrades() {
        List<GeneralsUpgrade> upgrades = generalsUpgradeRepository.findAll();
        return upgrades.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public GeneralsUpgradeDTO createGeneralsUpgrade(GeneralsUpgradeDTO request) {
        GeneralsUpgrade upgrade = new GeneralsUpgrade();
        upgrade.setGeneralTemplateId(request.getGeneralTemplateId());
        upgrade.setLevel(request.getLevel());
        upgrade.setRequiredExperience(request.getRequiredExperience());
        upgrade.setAttackIncrease(request.getAttackIncrease());
        upgrade.setDefenseIncrease(request.getDefenseIncrease());
        upgrade.setTroopsIncrease(request.getTroopsIncrease());
        upgrade.setSpeedIncrease(request.getSpeedIncrease());
        upgrade.setSkillUnlock(request.getSkillUnlock());
        upgrade.setTalentUnlock(request.getTalentUnlock());

        generalsUpgradeRepository.save(upgrade);
        return convertToDTO(upgrade);
    }

    @Override
    public GeneralsUpgradeDTO updateGeneralsUpgrade(Long id, GeneralsUpgradeDTO request) {
        GeneralsUpgrade upgrade = generalsUpgradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeneralsUpgrade not found with ID: " + id));

        upgrade.setLevel(request.getLevel());
        upgrade.setRequiredExperience(request.getRequiredExperience());
        upgrade.setAttackIncrease(request.getAttackIncrease());
        upgrade.setDefenseIncrease(request.getDefenseIncrease());
        upgrade.setTroopsIncrease(request.getTroopsIncrease());
        upgrade.setSpeedIncrease(request.getSpeedIncrease());
        upgrade.setSkillUnlock(request.getSkillUnlock());
        upgrade.setTalentUnlock(request.getTalentUnlock());

        generalsUpgradeRepository.save(upgrade);
        return convertToDTO(upgrade);
    }

    @Override
    public void deleteGeneralsUpgrade(Long id) {
        GeneralsUpgrade upgrade = generalsUpgradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeneralsUpgrade not found with ID: " + id));
        generalsUpgradeRepository.delete(upgrade);
    }

    // DTO转换方法
    private GeneralsUpgradeDTO convertToDTO(GeneralsUpgrade upgrade) {
        GeneralsUpgradeDTO dto = new GeneralsUpgradeDTO();
        dto.setGeneralUpgradeId(upgrade.getId());
        dto.setGeneralTemplateId(upgrade.getGeneralTemplateId());
        dto.setLevel(upgrade.getLevel());
        dto.setRequiredExperience(upgrade.getRequiredExperience());
        dto.setAttackIncrease(upgrade.getAttackIncrease());
        dto.setDefenseIncrease(upgrade.getDefenseIncrease());
        dto.setTroopsIncrease(upgrade.getTroopsIncrease());
        dto.setSpeedIncrease(upgrade.getSpeedIncrease());
        dto.setSkillUnlock(upgrade.getSkillUnlock());
        dto.setTalentUnlock(upgrade.getTalentUnlock());
        return dto;
    }
}
