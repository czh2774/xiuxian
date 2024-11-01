package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CharacterItemControllerTest {

    @InjectMocks
    private CharacterItemController characterItemController;

    @Mock
    private CharacterItemService characterItemService;

    @Mock
    private HttpServletRequest request;

    private CharacterItemDTO characterItemDTO;
    private IdRequestDTO idRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        characterItemDTO = new CharacterItemDTO();
        characterItemDTO.setId(1L);
        characterItemDTO.setCharacterId(100L);
        characterItemDTO.setItemTemplateId(10L);
        characterItemDTO.setQuantity(5);

        idRequestDTO = new IdRequestDTO();
        idRequestDTO.setId(100L);
    }

    /**
     * 测试获取角色的所有道具信息
     */
    @Test
    void getCharacterItemsByCharacterId_Success() {
        // Mock 服务层返回数据
        List<CharacterItemDTO> items = Collections.singletonList(characterItemDTO);
        when(characterItemService.getCharacterItemsByCharacterId(anyLong())).thenReturn(items);

        // Mock 请求行为
        when(request.getRequestURI()).thenReturn("/character/item/list");

        // 调用控制器方法
        ResponseEntity<CustomApiResponse<List<CharacterItemDTO>>> response = characterItemController.getCharacterItemsByCharacterId(idRequestDTO, request);

        // 验证响应
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("成功获取角色道具信息", response.getBody().getMessage());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(characterItemDTO, response.getBody().getData().get(0));
    }

    /**
     * 测试获取单个道具的详细信息
     */
    @Test
    void getCharacterItemById_Success() {
        // Mock 服务层返回数据
        when(characterItemService.getCharacterItemById(anyLong())).thenReturn(characterItemDTO);

        // Mock 请求行为
        when(request.getRequestURI()).thenReturn("/character/item/getById");

        // 调用控制器方法
        ResponseEntity<CustomApiResponse<CharacterItemDTO>> response = characterItemController.getCharacterItemById(idRequestDTO, request);

        // 验证响应
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("成功获取道具详细信息", response.getBody().getMessage());
        assertEquals(characterItemDTO, response.getBody().getData());
    }


}
