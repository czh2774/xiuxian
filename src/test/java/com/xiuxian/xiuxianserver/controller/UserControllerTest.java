package com.xiuxian.xiuxianserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiuxian.xiuxianserver.entity.UserModel;
import com.xiuxian.xiuxianserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper(); // 用于将对象转换为 JSON

    private UserModel testUser;

    @BeforeEach
    void setUp() {
        // 初始化 Mockito 的模拟对象
        MockitoAnnotations.openMocks(this);

        // 设置 MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // 初始化一个测试用户对象
        testUser = UserModel.builder()
                .id(1001L)
                .platformUserId(1846320926338191360L)
                .name("TestUser")
                .build();
    }

    @Test
    void testGetUserByPlatformUserId() throws Exception {
        // 模拟 userService 返回数据
        when(userService.findByPlatformUserId(1846320926338191360L)).thenReturn(testUser);

        mockMvc.perform(post("/user/getUser")
                        .param("platformUserId", "wx_openid_12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value(1001L))
                .andExpect(jsonPath("$.platformUserId").value("wx_openid_12345"))
                .andExpect(jsonPath("$.name").value("TestUser"));
    }

    @Test
    void testCreateUser() throws Exception {
        // 模拟 userService 保存用户并返回用户
        when(userService.createUser(any(UserModel.class))).thenReturn(testUser);

        mockMvc.perform(post("/user/create")
                        .content(objectMapper.writeValueAsString(testUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value(1001L))
                .andExpect(jsonPath("$.platformUserId").value("wx_openid_12345"))
                .andExpect(jsonPath("$.name").value("TestUser"));
    }

    @Test
    void testUpdateUser() throws Exception {
        // 模拟 userService 更新用户并返回更新后的用户
        when(userService.updateUser(any(UserModel.class))).thenReturn(testUser);

        mockMvc.perform(post("/user/update")
                        .content(objectMapper.writeValueAsString(testUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value(1001L))
                .andExpect(jsonPath("$.platformUserId").value("wx_openid_12345"))
                .andExpect(jsonPath("$.name").value("TestUser"));
    }

    @Test
    void testDeleteUser() throws Exception {
        // 模拟 userService 删除用户
        doNothing().when(userService).deleteUser(1001L);

        mockMvc.perform(post("/user/delete")
                        .param("playerId", "1001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
