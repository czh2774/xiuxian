package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CharacterProfileController
 * 处理与角色档案相关的请求，并将其转发到服务层进行处理。
 */
@RestController
@RequestMapping("/profiles")
public class CharacterProfileController {

    private final CharacterProfileService characterProfileService;

    public CharacterProfileController(CharacterProfileService characterProfileService) {
        this.characterProfileService = characterProfileService;
    }

    /**
     * 创建新的角色档案
     *
     * @param createDTO 创建角色档案请求的DTO
     * @return 创建成功后的角色档案DTO
     */
    @PostMapping("/create")
    @Operation(summary = "创建新的角色档案", description = "创建新角色时使用")
    @ApiResponse(responseCode = "201", description = "角色档案创建成功")
    public CharacterProfileDTO createProfile(@RequestBody CharacterProfileCreateDTO createDTO) {
        return characterProfileService.createCharacterProfile(createDTO);
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
        return characterProfileService.getCharacterProfileAllInfo(requestDTO.getCharacterId());
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
