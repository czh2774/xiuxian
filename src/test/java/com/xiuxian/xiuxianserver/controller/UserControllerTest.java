package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.entity.UserModel;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.service.UserService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import com.xiuxian.xiuxianserver.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest request;

    private String validToken = "valid.token";
    private Long platformUserId = 1L;
    private UserModel user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserModel();
        user.setId(platformUserId);
        user.setName("Test User");
    }

    @Test
    void getUser_Success() {
        // Mock JWT Token extraction and validation
        when(jwtTokenUtil.getClaim(validToken, "platformUserId", Long.class)).thenReturn(platformUserId);
        when(jwtTokenUtil.isTokenValid(validToken)).thenReturn(true);
        when(userService.findByPlatformUserId(platformUserId)).thenReturn(user);

        // Mock request behavior
        when(request.getRequestURI()).thenReturn("/user/getUser");

        // Mock SecurityContext and Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn(validToken);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Call the method
        ResponseEntity<CustomApiResponse<UserModel>> response = userController.getUser(request);

        // Validate response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("用户信息获取成功", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void getUser_TokenMissing() {
        // Mock request behavior
        when(request.getRequestURI()).thenReturn("/user/getUser");

        // Mock SecurityContext without authentication
        SecurityContextHolder.clearContext();

        // Call the method
        ResponseEntity<CustomApiResponse<UserModel>> response = userController.getUser(request);

        // Validate response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("JWT Token 缺失", response.getBody().getMessage());
    }

    @Test
    void getUser_InvalidToken() {
        // Mock JWT Token extraction and validation
        when(jwtTokenUtil.getClaim(validToken, "platformUserId", Long.class)).thenReturn(platformUserId);
        when(jwtTokenUtil.isTokenValid(validToken)).thenReturn(false);

        // Mock request behavior
        when(request.getRequestURI()).thenReturn("/user/getUser");

        // Mock SecurityContext and Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn(validToken);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Call the method
        ResponseEntity<CustomApiResponse<UserModel>> response = userController.getUser(request);

        // Validate response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("JWT Token 无效或已过期", response.getBody().getMessage());
    }

    @Test
    void getUser_UserNotFound() {
        // Mock JWT Token extraction and validation
        when(jwtTokenUtil.getClaim(validToken, "platformUserId", Long.class)).thenReturn(platformUserId);
        when(jwtTokenUtil.isTokenValid(validToken)).thenReturn(true);
        when(userService.findByPlatformUserId(platformUserId)).thenReturn(null);

        // Mock request behavior
        when(request.getRequestURI()).thenReturn("/user/getUser");

        // Mock SecurityContext and Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn(validToken);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Call the method and expect exception
        try {
            userController.getUser(request);
        } catch (ResourceNotFoundException e) {
            assertEquals("用户未找到", e.getMessage());
        }
    }
}
