package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CharacterBuildingServiceImpl 实现角色建筑实例的相关业务逻辑。
 */
@Service
public class CharacterBuildingServiceImpl implements CharacterBuildingService {

    private final CharacterBuildingRepository characterBuildingRepository;

    public CharacterBuildingServiceImpl(CharacterBuildingRepository characterBuildingRepository) {
        this.characterBuildingRepository = characterBuildingRepository;
    }

    /**
     * 获取所有角色的建筑实例
     * @return 角色建筑实例列表
     */
    @Override
    public List<CharacterBuildingDTO> getAllCharacterBuildings() {
        return characterBuildingRepository.findAll()
                .stream()
                .map(CharacterBuildingDTO::new)
                .collect(Collectors.toList());
    }
    /**
     * 完成建筑升级操作的方法。
     *
     * 该方法用于处理角色建筑升级完成的操作。当建筑升级完成时，建筑的当前等级会增加 1，
     * 同时建筑的状态会从正在升级（如升级中或其他状态）变为“空闲”（IDLE），表示建筑可以进行下一个操作。
     *
     * @param buildingId 角色建筑实例的唯一标识符（ID）
     * @throws ResourceNotFoundException 如果没有找到对应 ID 的角色建筑实例，则抛出资源未找到异常
     */
    @Override
    public void completeBuildingUpgrade(Long buildingId) {
        // 根据建筑ID查找建筑实例，如果找不到则抛出 ResourceNotFoundException 异常
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("建筑实例未找到，ID: " + buildingId));

        // 升级建筑等级，每次调用此方法时建筑等级加1
        building.setCurrentLevel(building.getCurrentLevel() + 1);

        // 更新建筑状态为“空闲”，表示升级操作已完成，建筑可以继续进行其他操作
        building.setBuildingStatus(BuildingStatusType.IDLE);

        // 将更新后的建筑信息保存回数据库
        characterBuildingRepository.save(building);
    }
    /**
     * 更新建筑实例的功能提示。
     *
     * 该方法用于更新指定建筑实例的功能提示信息。功能提示可能包括：收集资源、可以升级等信息。
     * 调用此方法时，系统会根据传入的提示类型（`FeaturePromptType`），更新该建筑的当前功能提示。
     *
     * @param buildingId 角色建筑实例的唯一标识符（ID）
     * @param newPrompt  要设置的新功能提示类型（`FeaturePromptType` 枚举）
     * @throws ResourceNotFoundException 如果没有找到对应 ID 的角色建筑实例，则抛出资源未找到异常
     */
    @Override
    public void updateFeaturePrompt(Long buildingId, FeaturePromptType newPrompt) {
        // 根据建筑ID查找建筑实例，如果找不到则抛出 ResourceNotFoundException 异常
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("建筑实例未找到，ID: " + buildingId));

        // 更新该建筑的功能提示信息为传入的新功能提示
        building.setFeaturePrompt(newPrompt);

        // 将更新后的建筑信息保存回数据库
        characterBuildingRepository.save(building);
    }

    /**
     * 根据角色ID获取该角色的所有建筑实例
     * @param characterId 角色ID
     * @return 角色建筑实例列表
     */
    @Override
    public List<CharacterBuildingDTO> getCharacterBuildingsByCharacterId(Long characterId) {
        return characterBuildingRepository.findByCharacterId(characterId)
                .stream()
                .map(CharacterBuildingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 根据建筑ID获取建筑实例
     * @param buildingId 建筑实例ID
     * @return 建筑实例DTO
     * @throws ResourceNotFoundException 如果建筑实例未找到
     */
    @Override
    public CharacterBuildingDTO getCharacterBuildingById(Long buildingId) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("建筑实例未找到，ID: " + buildingId));
        return new CharacterBuildingDTO(building);
    }

    /**
     * 创建新的角色建筑实例
     * @param request 创建请求对象
     * @return 创建的建筑实例DTO
     */
    @Override
    public CharacterBuildingDTO createCharacterBuildingInstance(CharacterBuildingCreateRequestDTO request) {
        if (request.getLocationId() == null) {
            throw new IllegalArgumentException("Location ID cannot be null");
        }

        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterId(request.getCharacterId());
        building.setBuildingTemplateId(request.getBuildingTemplateId());
        building.setLocationId(request.getLocationId());
        building.setCurrentLevel(1);  // 初始等级为1
        building.setBuildingStatus(BuildingStatusType.IDLE);  // 默认状态为空闲

        // 保存建筑实例
        CharacterBuilding savedBuilding = characterBuildingRepository.save(building);

        return new CharacterBuildingDTO(savedBuilding);
    }
    /**
     * 更新建筑实例的状态
     * @param buildingId 建筑实例ID
     * @param newStatus 新的建筑状态
     * @throws ResourceNotFoundException 如果建筑实例未找到
     */
    @Override
    public void updateCharacterBuildingStatus(Long buildingId, BuildingStatusType newStatus) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("建筑实例未找到，ID: " + buildingId));

        // 更新状态
        building.setBuildingStatus(newStatus);
        characterBuildingRepository.save(building);
    }


}
