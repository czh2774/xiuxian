package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CharacterBuildingServiceImpl 实现了角色建筑实例相关的业务逻辑。
 */
@Service
@RequiredArgsConstructor
public class CharacterBuildingServiceImpl implements CharacterBuildingService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingServiceImpl.class);

    private final CharacterBuildingRepository characterBuildingRepository;
    private final Snowflake snowflake;  // 注入 Snowflake ID 生成器

    /**
     * 获取所有的角色建筑实例。
     *
     * @return 角色建筑实例列表
     */
    @Override
    public List<CharacterBuildingDTO> getAllCharacterBuildings() {
        logger.info("获取所有角色建筑实例");

        List<CharacterBuilding> buildings = characterBuildingRepository.findAll();
        return buildings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色ID获取该角色的所有建筑实例。
     *
     * @param characterId 角色的唯一标识符
     * @return 角色建筑实例列表
     */
    @Override
    public List<CharacterBuildingDTO> getCharacterBuildingsByCharacterId(Long characterId) {
        logger.info("获取角色ID为 {} 的所有建筑实例", characterId);

        List<CharacterBuilding> buildings = characterBuildingRepository.findByCharacterId(characterId);
        if (buildings.isEmpty()) {
            logger.warn("未找到角色ID为 {} 的建筑实例", characterId);
            throw new ResourceNotFoundException("未找到角色ID为 " + characterId + " 的建筑实例");
        }

        return buildings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 根据建筑ID获取单个角色建筑实例。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     * @return 对应的角色建筑实例
     */
    @Override
    public CharacterBuildingDTO getCharacterBuildingById(Long buildingId) {
        logger.info("获取建筑ID为 {} 的角色建筑实例", buildingId);

        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));

        return convertToDTO(building);
    }

    /**
     * 创建新的角色建筑实例。
     *
     * @param request 创建建筑请求的DTO对象
     * @return 新建的角色建筑实例信息
     */
    @Override
    public CharacterBuildingDTO createCharacterBuildingInstance(CharacterBuildingCreateRequestDTO request) {
        logger.info("创建角色ID为 {} 的建筑实例，模板ID为 {}", request.getCharacterId(), request.getBuildingTemplateId());

        CharacterBuilding building = new CharacterBuilding();
        building.setId(snowflake.nextId());  // 使用 Snowflake 生成唯一ID
        building.setCharacterId(request.getCharacterId());
        building.setBuildingTemplateId(request.getBuildingTemplateId());
        building.setLocationId(request.getLocationId());
        building.setCurrentLevel(1);  // 初始等级
        building.setBuildingStatus(BuildingStatusType.IDLE);

        characterBuildingRepository.save(building);
        logger.info("成功创建角色建筑实例，建筑ID为 {}", building.getId());

        return convertToDTO(building);
    }

    /**
     * 更新角色建筑的状态。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     * @param newStatus  要更新的状态
     */
    @Override
    public void updateCharacterBuildingStatus(Long buildingId, BuildingStatusType newStatus) {
        logger.info("更新建筑ID为 {} 的角色建筑状态为 {}", buildingId, newStatus);

        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));

        building.setBuildingStatus(newStatus);
        characterBuildingRepository.save(building);
        logger.info("成功更新建筑ID为 {} 的状态为 {}", buildingId, newStatus);
    }

    /**
     * 完成建筑升级操作。
     *
     * 当建筑升级完成时，更新建筑的当前等级并将建筑状态设置为空闲（IDLE）。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     */
    @Override
    public void completeBuildingUpgrade(Long buildingId) {
        logger.info("完成建筑ID为 {} 的升级操作", buildingId);

        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));

        building.setCurrentLevel(building.getCurrentLevel() + 1);
        building.setBuildingStatus(BuildingStatusType.IDLE);
        characterBuildingRepository.save(building);

        logger.info("建筑ID为 {} 的升级完成，当前等级为 {}", buildingId, building.getCurrentLevel());
    }

    /**
     * 更新建筑实例的功能提示信息。
     *
     * 更新角色建筑的功能提示信息，用于显示提示给玩家，如“可以升级”、“收集资源”等。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     * @param newPrompt  新的功能提示类型
     */
    @Override
    public void updateFeaturePrompt(Long buildingId, FeaturePromptType newPrompt) {
        logger.info("更新建筑ID为 {} 的功能提示为 {}", buildingId, newPrompt);

        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));

        building.setFeaturePrompt(newPrompt);
        characterBuildingRepository.save(building);

        logger.info("成功更新建筑ID为 {} 的功能提示为 {}", buildingId, newPrompt);
    }

    /**
     * 将 CharacterBuilding 实体转换为 DTO。
     *
     * @param building 实体对象
     * @return DTO对象
     */
    private CharacterBuildingDTO convertToDTO(CharacterBuilding building) {
        return new CharacterBuildingDTO(
                building.getId(),
                building.getCharacterId(),
                building.getBuildingTemplateId(),
                building.getLocationId(),
                building.getCurrentLevel(),
                building.getBuildingStatus(),
                building.getActionStartTime(),
                building.getActionTotalDuration(),
                building.getTransactionId(),
                building.getFeaturePrompt(),
                building.isRewardPending(),
                building.getGrowthType(),
                building.getGrowthId(),
                building.getGrowthValue()
        );
    }

    /**
     * 为指定角色创建默认的建筑。
     * 当一个新角色创建时，会为其分配一系列默认的建筑。
     *
     * @param characterId 新创建的角色的ID
     */
    @Override
    public void createDefaultBuildingsForCharacter(Long characterId) {
        logger.info("为角色ID {} 创建默认建筑", characterId);

        // 假设默认的建筑模板ID和位置ID
        Long defaultBuildingTemplateId = 1001L;  // 假设默认的建筑模板ID为1
        Long defaultLocationId = 1L;  // 假设默认的建筑位置ID为1

        // 创建第一个默认建筑
        CharacterBuilding building1 = new CharacterBuilding();
        building1.setId(snowflake.nextId());  // 使用Snowflake生成唯一的建筑ID
        building1.setCharacterId(characterId);  // 设置建筑所属的角色ID
        building1.setBuildingTemplateId(defaultBuildingTemplateId);  // 设置建筑的模板ID
        building1.setLocationId(defaultLocationId);  // 设置建筑的位置ID
        building1.setCurrentLevel(1);  // 设置建筑的初始等级为1
        building1.setBuildingStatus(BuildingStatusType.IDLE);  // 设置建筑的状态为“空闲”

        // 保存创建的建筑到数据库
        characterBuildingRepository.save(building1);

        // 记录成功创建的日志
        logger.info("成功为角色ID {} 创建了默认建筑，建筑ID: {}", characterId, building1.getId());

        // 如果需要，可以重复创建更多的默认建筑
        // 例如创建第二个建筑，可以依次改变 buildingTemplateId 和 locationId
    }


}
