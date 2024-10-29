package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.service.impl.CharacterProfileServiceImpl;
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
import java.util.List;
import java.util.Map;

import static com.xiuxian.xiuxianserver.util.UserContextUtil.getCurrentUser;

/**
 * CharacterProfileController
 * 处理与角色档案相关的请求，并将其转发到服务层进行处理。
 */
@RestController
@RequestMapping("/profiles")
public class CharacterProfileController {
    private static final Logger logger = LoggerFactory.getLogger(CharacterProfileController.class);

    private final CharacterProfileService characterProfileService;
    private final CharacterBuildingService characterBuildingService;
    public CharacterProfileController(CharacterProfileService characterProfileService,CharacterBuildingService characterBuildingService) {
        this.characterProfileService = characterProfileService;
        this.characterBuildingService=characterBuildingService;
    }
    /**
     * 检查当前用户是否有角色
     * 返回一个布尔值和角色信息（如果存在）
     */
    @PostMapping("/hasCharacter")
    @Operation(summary = "检查当前用户是否有角色", description = "返回用户是否拥有角色")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回用户是否有角色的状态"),
            @ApiResponse(responseCode = "401", description = "用户未授权")
    })
    public ResponseEntity<CustomApiResponse<Map<String, Object>>> hasCharacter(HttpServletRequest request) {
        UserDTO currentUser = getCurrentUser();  // 从上下文中获取当前用户
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CustomApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "未找到用户信息", request.getRequestURI()));
        }

        Long platformUserId = currentUser.getPlatformUserId();

        // 使用 existsByPlayerId 方法来检查是否有角色
        boolean hasCharacter = characterProfileService.existsByPlayerId(platformUserId);

        // 构建响应数据
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("hasCharacter", hasCharacter);

        // 如果有角色，查询并添加角色信息
        if (hasCharacter) {
            CharacterProfileDTO characterData = characterProfileService.getCharacterByPlayerId(platformUserId);  // 假设有该方法
            responseData.put("characterData", characterData);
        }

        // 返回响应
        return ResponseEntity.ok(CustomApiResponse.success("用户角色状态检查成功", responseData, request.getRequestURI()));
    }






    @PostMapping("/create")
    @Operation(summary = "创建新的角色档案", description = "创建新角色时使用")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "角色档案创建成功"),
            @ApiResponse(responseCode = "409", description = "角色名称已存在或用户已创建过角色")
    })
    public ResponseEntity<CustomApiResponse<CharacterProfileDTO>> createProfile(@RequestBody @Valid CharacterProfileCreateDTO createDTO, HttpServletRequest request) {
        // 开始创建角色日志
        logger.info("接收到创建角色请求, 请求来源: {}, 角色名称: {}", request.getRemoteAddr(), createDTO.getName());

        // 检查当前用户是否登录
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            logger.error("用户未登录，无法创建角色");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CustomApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "未找到用户信息", null, request.getRequestURI()));
        }

        Long platformUserId = currentUser.getPlatformUserId();  // 获取用户的 platformUserId（playId）
        logger.info("当前用户ID: {}", platformUserId);

        // 检查用户是否已经创建角色
        if (characterProfileService.existsByPlayerId(platformUserId)) {
            logger.warn("用户已经创建了角色，无法重复创建。用户ID: {}", platformUserId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomApiResponse<>(HttpStatus.CONFLICT.value(), "用户已创建角色", null, request.getRequestURI()));
        }

        // 检查角色名是否唯一
        if (characterProfileService.existsByName(createDTO.getName())) {
            logger.warn("角色名称已存在，无法使用该名称创建新角色。角色名称: {}", createDTO.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomApiResponse<>(HttpStatus.CONFLICT.value(), "角色名已存在", null, request.getRequestURI()));
        }

        // 创建角色档案
        CharacterProfileDTO profile;
        try {
            logger.info("开始创建角色档案...");
            profile = characterProfileService.createCharacterProfile(createDTO);
            logger.info("角色档案创建成功，角色ID: {}", profile.getId());

            // 创建默认的角色建筑
            logger.info("为角色ID {} 创建默认建筑", profile.getId());
            characterBuildingService.createDefaultBuildingsForCharacter(profile.getId());  // 调用服务层创建默认建筑

        } catch (Exception e) {
            logger.error("创建角色档案时发生错误: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "创建角色失败", null, request.getRequestURI()));
        }

        // 返回成功响应
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomApiResponse<>(HttpStatus.CREATED.value(), "角色档案创建成功", profile, request.getRequestURI()));
    }




    /**
     * 根据角色ID获取角色档案的所有信息
     *
     * @param requestDTO 包含角色ID的请求DTO
     * @return 角色的基本信息
     */
    @PostMapping("/details")
    @Operation(summary = "获取角色的所有信息", description = "根据角色ID查询角色的所有信息")
    @ApiResponse(responseCode = "200", description = "获取角色的所有信息成功")
    public CharacterProfileDTO getProfileDetails(@RequestBody CharacterProfileDTO requestDTO) {
        return characterProfileService.getCharacterProfileAllInfo(requestDTO.getId());
    }

    @PostMapping("/checkName")
    @Operation(summary = "校验角色名称", description = "检查角色名称是否唯一")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "角色名称可用"),
            @ApiResponse(responseCode = "409", description = "角色名称已存在")
    })
    public ResponseEntity<CustomApiResponse<BoolResponseDTO>> checkName(@RequestBody @Valid CharacterProfileNameCheckDTO nameCheckDTO, HttpServletRequest request) {
        boolean nameExists = characterProfileService.existsByName(nameCheckDTO.getName());

        // 用 BoolResponseDTO 封装布尔值，字段为 data
        BoolResponseDTO responseDTO = new BoolResponseDTO(!nameExists);

        if (nameExists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new CustomApiResponse<>(HttpStatus.CONFLICT.value(), "角色名称已存在", responseDTO, request.getRequestURI()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CustomApiResponse<>(HttpStatus.OK.value(), "角色名称可用", responseDTO, request.getRequestURI()));
        }
    }





    /**
     * 根据角色ID获取角色档案的基本信息
     *
     * @param requestDTO 包含角色ID的请求DTO
     * @return 角色的基本信息
     */
    @PostMapping("/info")
    @Operation(summary = "获取角色的基本信息", description = "根据角色ID查询角色的基本信息")
    @ApiResponse(responseCode = "200", description = "获取角色的基本信息成功")
    public CharacterProfileBasicInfoDTO getProfileInfo(@RequestBody CharacterIdRequestDTO requestDTO) {
        return characterProfileService.getCharacterProfileBasicInfo(requestDTO.getCharacterId());
    }

    /**
     * 根据角色ID获取角色的资源信息
     *
     * @param requestDTO 包含角色ID的请求DTO
     * @return 角色的资源信息
     */
    @PostMapping("/resourceInfo")
    @Operation(summary = "获取角色的资源信息", description = "根据角色ID查询角色的资源信息")
    @ApiResponse(responseCode = "200", description = "获取角色的资源信息成功")
    public CharacterProfileResourceInfoDTO getProfileResourceInfo(@RequestBody CharacterIdRequestDTO requestDTO) {
        return characterProfileService.getCharacterProfileResourceInfo(requestDTO.getCharacterId());
    }

    /**
     * 更新角色的资源信息
     *
     * @param updateDTO 角色资源更新请求DTO
     */
    @PostMapping("/updateResource")
    @Operation(summary = "更新角色的资源信息", description = "更新角色的资源信息，如金币、经验等")
    @ApiResponse(responseCode = "200", description = "角色资源更新成功")
    public void updateProfileResource(@RequestBody CharacterProfileResourceUpdateDTO updateDTO) {
        characterProfileService.updateCharacterProfileResource(updateDTO);
    }

    /**
     * 更新角色派系信息
     *
     * @param factionUpdateDTO 角色派系更新请求DTO
     */
    @PostMapping("/updateFaction")
    @Operation(summary = "更新角色的派系信息", description = "更新角色的派系，如加入新的派系")
    @ApiResponse(responseCode = "200", description = "角色派系更新成功")
    public void updateProfileFaction(@RequestBody CharacterProfileFactionUpdateDTO factionUpdateDTO) {
        characterProfileService.updateCharacterFaction(factionUpdateDTO);
    }

    /**
     * 部分更新角色的档案信息
     *
     * @param partialUpdateDTO 角色档案部分更新请求DTO
     * @return 更新后的角色档案信息
     */
    @PostMapping("/partialUpdate")
    @Operation(summary = "部分更新角色档案信息", description = "部分更新角色档案信息")
    @ApiResponse(responseCode = "200", description = "角色档案部分更新成功")
    public CharacterProfileDTO partialUpdateProfile(@RequestBody CharacterProfilePartialUpdateDTO partialUpdateDTO) {
        return characterProfileService.partialUpdateCharacterProfile(partialUpdateDTO);
    }

    /**
     * 完整更新角色的档案信息
     *
     * @param updateDTO 角色档案更新请求DTO
     * @return 更新后的角色档案信息
     */
    @PostMapping("/update")
    @Operation(summary = "更新角色档案信息", description = "完整更新角色档案信息")
    @ApiResponse(responseCode = "200", description = "角色档案更新成功")
    public CharacterProfileDTO updateProfile(@Valid @RequestBody CharacterProfileUpdateDTO updateDTO) {
        return characterProfileService.updateCharacterProfile(updateDTO);
    }

    /**
     * 删除角色档案
     *
     * @param requestDTO 包含角色ID的请求DTO
     */
    @PostMapping("/delete")
    @Operation(summary = "删除角色档案", description = "根据角色ID删除角色档案")
    @ApiResponse(responseCode = "204", description = "角色档案删除成功")
    public void deleteProfile(@RequestBody CharacterIdRequestDTO requestDTO) {
        characterProfileService.deleteCharacterProfile(requestDTO.getCharacterId());
    }
}
