package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.xiuxian.xiuxianserver.util.UserContextUtil.getCurrentUser;

@RestController
@RequestMapping("/profiles")
public class CharacterProfileController {

    private static final Logger logger = LoggerFactory.getLogger(CharacterProfileController.class);
    private final CharacterProfileService characterProfileService;

    public CharacterProfileController(CharacterProfileService characterProfileService) {
        this.characterProfileService = characterProfileService;
    }

    /**
     * 检查当前用户是否有角色
     */
    @PostMapping("/hasCharacter")
    @Operation(summary = "检查当前用户是否有角色", description = "返回用户是否拥有角色")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回用户是否有角色的状态"),
            @ApiResponse(responseCode = "401", description = "用户未授权")
    })
    public ResponseEntity<CustomApiResponse<Map<String, Object>>> hasCharacter(HttpServletRequest request) {
        var currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CustomApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "未找到用户信息", request.getRequestURI()));
        }
        Long platformUserId = currentUser.getPlatformUserId();
        boolean hasCharacter = characterProfileService.existsByPlayerId(platformUserId);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("hasCharacter", hasCharacter);
        if (hasCharacter) {
            var characterData = characterProfileService.getCharacterByPlayerId(platformUserId);
            responseData.put("characterData", characterData);
        }
        return ResponseEntity.ok(CustomApiResponse.success("用户角色状态检查成功", responseData, request.getRequestURI()));
    }

    /**
     * 创建新的角色档案
     */
    @PostMapping("/create")
    @Operation(summary = "创建新的角色档案", description = "创建新角色时使用")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "角色档案创建成功"),
            @ApiResponse(responseCode = "409", description = "角色名称已存在或用户已创建过角色")
    })
    public ResponseEntity<CustomApiResponse<CharacterProfileDTO>> createProfile(@RequestBody @Valid CharacterProfileCreateDTO createDTO, HttpServletRequest request) {
        logger.info("接收到创建角色请求, 请求来源: {}, 角色名称: {}", request.getRemoteAddr(), createDTO.getName());

        var currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CustomApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "未找到用户信息", request.getRequestURI()));
        }
        Long platformUserId = currentUser.getPlatformUserId();
        if (characterProfileService.existsByPlayerId(platformUserId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(CustomApiResponse.error(HttpStatus.CONFLICT.value(), "用户已创建角色", request.getRequestURI()));
        }
        if (characterProfileService.existsByName(createDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(CustomApiResponse.error(HttpStatus.CONFLICT.value(), "角色名已存在", request.getRequestURI()));
        }
        var profile = characterProfileService.createCharacterProfile(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomApiResponse.success("角色档案创建成功", profile, request.getRequestURI()));
    }

    /**
     * 完整更新角色的档案信息
     */
    @PostMapping("/update")
    @Operation(summary = "更新角色档案信息", description = "完整更新角色档案信息")
    @ApiResponse(responseCode = "200", description = "角色档案更新成功")
    public CharacterProfileDTO updateProfile(@Valid @RequestBody CharacterProfileUpdateDTO updateDTO) {
        return characterProfileService.updateCharacterProfile(updateDTO);
    }

    /**
     * 删除角色档案
     */
    @PostMapping("/delete")
    @Operation(summary = "删除角色档案", description = "根据角色ID删除角色档案")
    @ApiResponse(responseCode = "204", description = "角色档案删除成功")
    public void deleteProfile(@RequestBody CharacterIdRequestDTO requestDTO) {
        characterProfileService.deleteCharacterProfile(requestDTO.getCharacterId());
    }
}
