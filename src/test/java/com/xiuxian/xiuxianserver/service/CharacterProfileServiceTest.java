package com.xiuxian.xiuxianserver.service;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.CharacterProfileCreateDTO;
import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import com.xiuxian.xiuxianserver.dto.CharacterProfilePartialUpdateDTO;
import com.xiuxian.xiuxianserver.dto.CharacterProfileUpdateDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.impl.CharacterProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterProfileServiceTest {

    @Mock
    private CharacterProfileRepository characterProfileRepository;

    @Mock
    private Snowflake snowflake;  // 模拟 Snowflake 对象

    @InjectMocks
    private CharacterProfileServiceImpl characterProfileService;

    @BeforeEach
    void setUp() {
        // 初始化 Mockito 模拟对象
        MockitoAnnotations.openMocks(this);

        // 模拟 snowflake.nextId() 返回值
        when(snowflake.nextId()).thenReturn(1L);
    }

    // 1. 测试角色创建
    @Test
    void testCreateCharacterProfile() {
        // 创建 DTO 对象
        CharacterProfileCreateDTO createDTO = new CharacterProfileCreateDTO();
        createDTO.setName("Test Character");
        createDTO.setFaction("Faction A");

        // 创建模拟的 CharacterProfile 实体对象
        CharacterProfile profile = new CharacterProfile();
        profile.setCharacterId(1L);  // 设置 ID

        // 模拟保存操作
        when(characterProfileRepository.save(any(CharacterProfile.class))).thenReturn(profile);

        // 调用服务层方法
        CharacterProfileDTO result = characterProfileService.createCharacterProfile(createDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1L, result.getCharacterId());
        verify(characterProfileRepository, times(1)).save(any(CharacterProfile.class));
    }

    // 2. 测试角色创建时角色名称太长
    @Test
    void testCreateCharacterProfileWithLongName() {
        CharacterProfileCreateDTO createDTO = new CharacterProfileCreateDTO();
        createDTO.setName("a".repeat(256)); // 超长名称
        createDTO.setFaction("Faction A");

        // 预期抛出 IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            characterProfileService.createCharacterProfile(createDTO);
        });
    }

    // 3. 测试角色档案不存在时的异常处理
//    @Test
//    void testFindCharacterProfileNotFound() {
//        Long characterId = 1L;
//
//        // 模拟数据库中不存在此角色档案
//        when(characterProfileRepository.findById(characterId)).thenReturn(Optional.empty());
//
//        // 预期抛出 ResourceNotFoundException
//        assertThrows(ResourceNotFoundException.class, () -> {
//            characterProfileService.findCharacterProfileById(characterId);
//        });
//    }

    // 4. 测试角色档案更新
    @Test
    void testUpdateCharacterProfile() {
        // 模拟存在的角色档案
        CharacterProfile existingProfile = new CharacterProfile();
        existingProfile.setCharacterId(1L);
        existingProfile.setName("Old Name");
        existingProfile.setFaction("Old Faction");

        CharacterProfileUpdateDTO updateDTO = new CharacterProfileUpdateDTO();
        updateDTO.setCharacterId(1L);
        updateDTO.setName("New Name");
        updateDTO.setFaction("New Faction");

        // 模拟查找和保存操作
        when(characterProfileRepository.findById(1L)).thenReturn(Optional.of(existingProfile));
        when(characterProfileRepository.save(any(CharacterProfile.class))).thenReturn(existingProfile);

        // 调用服务层的更新方法
        CharacterProfileDTO result = characterProfileService.updateCharacterProfile(updateDTO);

        // 验证更新后的信息
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("New Faction", result.getFaction());
        verify(characterProfileRepository, times(1)).save(existingProfile);
    }

    // 5. 测试删除角色档案
    @Test
    void testDeleteCharacterProfile() {
        Long characterId = 1L;

        // 模拟删除操作
        doNothing().when(characterProfileRepository).deleteById(characterId);

        // 调用删除方法
        characterProfileService.deleteCharacterProfile(characterId);

        // 验证删除操作调用了一次
        verify(characterProfileRepository, times(1)).deleteById(characterId);
    }

    // 6. 测试部分更新角色档案
    @Test
    void testPartialUpdateCharacterProfile() {
        // 模拟存在的角色档案
        CharacterProfile existingProfile = new CharacterProfile();
        existingProfile.setCharacterId(1L);
        existingProfile.setName("Original Name");

        // 模拟部分更新的 DTO 对象
        CharacterProfilePartialUpdateDTO partialUpdateDTO = new CharacterProfilePartialUpdateDTO();
        partialUpdateDTO.setCharacterId(1L);
        partialUpdateDTO.setName("Updated Name");

        // 模拟查找和保存
        when(characterProfileRepository.findById(1L)).thenReturn(Optional.of(existingProfile));
        when(characterProfileRepository.save(any(CharacterProfile.class))).thenReturn(existingProfile);

        // 调用部分更新方法
        CharacterProfileDTO result = characterProfileService.partialUpdateCharacterProfile(partialUpdateDTO);

        // 验证角色档案名称是否更新
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(characterProfileRepository, times(1)).save(existingProfile);
    }


}
