package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.service.impl.CharacterBuildingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * CharacterBuildingServiceTest
 * 该类用于测试 CharacterBuildingService 的各种功能，包括建筑创建、状态更新、获取建筑实例等。
 */
@DirtiesContext
public class CharacterBuildingServiceTest {

    @Mock
    private CharacterBuildingRepository characterBuildingRepository;

    @InjectMocks
    private CharacterBuildingServiceImpl characterBuildingService;

    @BeforeEach
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);
    }

    // ===================== 测试用例一：创建角色建筑实例 =====================
    /**
     * 测试创建角色建筑实例的正常情况
     */
    @Test
    public void testCreateCharacterBuildingInstance() {
        // 构造请求对象
        CharacterBuildingCreateRequestDTO requestDTO = new CharacterBuildingCreateRequestDTO();
        requestDTO.setCharacterId(1L);
        requestDTO.setBuildingTemplateId(101L);
        requestDTO.setLocationId(201L);

        // 模拟数据库保存行为
        when(characterBuildingRepository.save(any(CharacterBuilding.class))).thenAnswer(invocation -> {
            CharacterBuilding savedBuilding = invocation.getArgument(0);
            savedBuilding.setCharacterBuildingId(1L);  // 模拟ID生成
            return savedBuilding;
        });

        // 调用服务层的创建方法
        CharacterBuildingDTO result = characterBuildingService.createCharacterBuildingInstance(requestDTO);

        // 验证存储库的 save() 方法被正确调用了一次
        verify(characterBuildingRepository, times(1)).save(any(CharacterBuilding.class));

        // 断言返回的结果与期望一致
        assertNotNull(result);
        assertEquals(1L, result.getBuildingId());  // 验证ID
        assertEquals(requestDTO.getCharacterId(), result.getCharacterId());
        assertEquals(requestDTO.getBuildingTemplateId(), result.getBuildingTemplateId());
    }

    /**
     * 测试创建角色建筑实例时的无效位置ID情况
     */
    @Test
    public void testCreateCharacterBuildingInstanceInvalidLocationId() {
        CharacterBuildingCreateRequestDTO requestDTO = new CharacterBuildingCreateRequestDTO();
        requestDTO.setCharacterId(1L);
        requestDTO.setBuildingTemplateId(101L);
        requestDTO.setLocationId(null);  // 无效的位置ID

        // 测试是否抛出 IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            characterBuildingService.createCharacterBuildingInstance(requestDTO);
        });
    }

    // ===================== 测试用例二：更新角色建筑状态 =====================
    /**
     * 测试更新角色建筑状态的正常情况
     */
    @Test
    public void testUpdateCharacterBuildingStatusValidTransition() {
        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterBuildingId(1L);
        building.setBuildingStatus(BuildingStatusType.IDLE);

        // 模拟根据ID查找建筑实例
        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.of(building));

        // 调用更新状态的方法
        characterBuildingService.updateCharacterBuildingStatus(1L, BuildingStatusType.RESEARCHING);

        // 验证状态更新后的保存调用
        verify(characterBuildingRepository, times(1)).save(building);
        assertEquals(BuildingStatusType.RESEARCHING, building.getBuildingStatus());
    }

    /**
     * 测试更新不存在的建筑实例时抛出的异常
     */
    @Test
    public void testUpdateCharacterBuildingStatusBuildingNotFound() {
        // 模拟查找不到建筑实例
        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.empty());

        // 验证是否抛出 ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            characterBuildingService.updateCharacterBuildingStatus(1L, BuildingStatusType.RESEARCHING);
        });
    }

    // ===================== 测试用例三：根据角色ID获取角色所有建筑 =====================
    /**
     * 测试获取角色建筑实例列表的正常情况
     */
    @Test
    public void testGetCharacterBuildingsByCharacterId() {
        List<CharacterBuilding> buildings = new ArrayList<>();
        buildings.add(new CharacterBuilding());

        // 模拟根据角色ID查找建筑实例
        when(characterBuildingRepository.findByCharacterId(1L)).thenReturn(buildings);

        List<CharacterBuildingDTO> result = characterBuildingService.getCharacterBuildingsByCharacterId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());  // 确认返回的建筑列表包含正确的数量
    }

    /**
     * 测试当角色没有建筑实例时返回空列表
     */
    @Test
    public void testGetCharacterBuildingsByCharacterIdNoBuildings() {
        // 模拟角色ID没有任何建筑实例
        when(characterBuildingRepository.findByCharacterId(1L)).thenReturn(Collections.emptyList());

        List<CharacterBuildingDTO> result = characterBuildingService.getCharacterBuildingsByCharacterId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());  // 确认返回的是空列表
    }

    // ===================== 测试用例四：根据建筑ID获取建筑实例 =====================
    /**
     * 测试根据建筑ID获取建筑实例
     */
    @Test
    public void testGetCharacterBuildingById() {
        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterBuildingId(1L);

        // 模拟根据ID查找建筑实例
        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.of(building));

        CharacterBuildingDTO result = characterBuildingService.getCharacterBuildingById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBuildingId());  // 验证返回的ID是否正确
    }

    /**
     * 测试建筑ID不存在的情况
     */
    @Test
    public void testGetCharacterBuildingByIdNotFound() {
        // 模拟找不到对应的建筑实例
        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            characterBuildingService.getCharacterBuildingById(1L);
        });
    }

    // ===================== 测试用例五：异常处理 =====================
    /**
     * 测试保存失败时抛出的异常
     */
    @Test
    public void testSaveBuildingFailure() {
        when(characterBuildingRepository.save(any(CharacterBuilding.class)))
                .thenThrow(new RuntimeException("Database error"));

        CharacterBuildingCreateRequestDTO requestDTO = new CharacterBuildingCreateRequestDTO();
        requestDTO.setCharacterId(1L);
        requestDTO.setBuildingTemplateId(101L);
        requestDTO.setLocationId(201L);

        assertThrows(RuntimeException.class, () -> {
            characterBuildingService.createCharacterBuildingInstance(requestDTO);
        });
    }

    // ===================== 新增：边界条件测试 =====================
    /**
     * 测试创建角色建筑时的边界条件：最大等级
     */
    @Test
    public void testCreateCharacterBuildingWithBoundaryLevel() {
        CharacterBuildingCreateRequestDTO requestDTO = new CharacterBuildingCreateRequestDTO();
        requestDTO.setCharacterId(1L);
        requestDTO.setBuildingTemplateId(101L);
        requestDTO.setLocationId(201L);

        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterBuildingId(1L);
        building.setCurrentLevel(Integer.MAX_VALUE);  // 测试最大等级

        when(characterBuildingRepository.save(any(CharacterBuilding.class))).thenReturn(building);

        CharacterBuildingDTO result = characterBuildingService.createCharacterBuildingInstance(requestDTO);
        assertEquals(Integer.MAX_VALUE, result.getCurrentLevel());  // 检查最大等级
    }

    // ===================== 新增：并发测试 =====================
    /**
     * 测试并发更新角色建筑状态
     */
    @Test
    public void testConcurrentBuildingStatusUpdates() throws InterruptedException {
        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterBuildingId(1L);
        building.setBuildingStatus(BuildingStatusType.IDLE);

        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.of(building));

        // 模拟两个线程同时更新建筑状态
        Thread thread1 = new Thread(() -> characterBuildingService.updateCharacterBuildingStatus(1L, BuildingStatusType.RESEARCHING));
        Thread thread2 = new Thread(() -> characterBuildingService.updateCharacterBuildingStatus(1L, BuildingStatusType.UPGRADING));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(characterBuildingRepository, times(2)).save(building);  // 验证更新了两次
    }

    // ===================== 新增：升级与操作完成测试 =====================
    /**
     * 测试完成建筑升级
     */
    @Test
    public void testCompleteBuildingUpgrade() {
        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterBuildingId(1L);
        building.setCurrentLevel(1);
        building.setBuildingStatus(BuildingStatusType.UPGRADING);

        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.of(building));

        characterBuildingService.completeBuildingUpgrade(1L);

        assertEquals(2, building.getCurrentLevel());  // 验证升级后的等级
        assertEquals(BuildingStatusType.IDLE, building.getBuildingStatus());  // 验证状态更新为空闲
        verify(characterBuildingRepository, times(1)).save(building);
    }

    // ===================== 新增：功能提示测试 =====================
    /**
     * 测试功能提示更新
     */
    @Test
    public void testFeaturePromptUpdate() {
        CharacterBuilding building = new CharacterBuilding();
        building.setCharacterBuildingId(1L);
        building.setFeaturePrompt(FeaturePromptType.COLLECT_FOOD);

        when(characterBuildingRepository.findById(1L)).thenReturn(Optional.of(building));

        characterBuildingService.updateFeaturePrompt(1L, FeaturePromptType.UPGRADE_AVAILABLE);

        assertEquals(FeaturePromptType.UPGRADE_AVAILABLE, building.getFeaturePrompt());  // 验证功能提示更新
        verify(characterBuildingRepository, times(1)).save(building);
    }
}
