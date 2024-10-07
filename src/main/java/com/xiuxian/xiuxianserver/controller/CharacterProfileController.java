package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.util.SnowflakeIdGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CharacterProfileController 用于处理与角色相关的 API 请求。
 */
@RestController
@RequestMapping("/api/characters")
public class CharacterProfileController {

    private final CharacterProfileService characterProfileService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Autowired
    public CharacterProfileController(CharacterProfileService characterProfileService, SnowflakeIdGenerator snowflakeIdGenerator) {
        this.characterProfileService = characterProfileService;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    /**
     * 创建新角色
     * @param createDTO 包含新角色的相关信息
     * @return 创建成功的角色信息
     */
    @Operation(summary = "创建新角色", description = "通过提供必要的角色信息创建一个新角色")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "角色创建成功"),
            @ApiResponse(responseCode = "400", description = "请求参数有误")
    })
    @PostMapping("/create")
    public CharacterProfile createCharacter(@RequestBody CharacterProfileCreateDTO createDTO) {
        CharacterProfile characterProfile = new CharacterProfile();

        // 生成雪花ID
        long characterId = snowflakeIdGenerator.nextId();
        characterProfile.setCharacterId(characterId); // 设置生成的角色ID
        characterProfile.setStatus("normal"); // 或者您想要的其他默认值
        characterProfile.setPlayerId(createDTO.getPlayerId());
        characterProfile.setName(createDTO.getName());
        characterProfile.setFaction(createDTO.getFaction());

        // 其他属性可以设置为默认值或随机生成
        characterProfile.setAvatar("default_avatar_url");
        characterProfile.setCombatPower(0);
        characterProfile.setTitleLevel(0);
        characterProfile.setOfficialPosition("none");
        characterProfile.setCreatedAt(LocalDateTime.now());
        characterProfile.setUpdatedAt(LocalDateTime.now());

        // 调用服务层方法进行角色的创建
        return characterProfileService.createCharacterProfile(characterProfile);
    }

    /**
     * 根据ID获取角色
     * @param request 包含角色ID的请求体
     * @return 角色信息
     */
    @Operation(summary = "根据ID获取角色", description = "根据角色ID获取角色信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取角色信息"),
            @ApiResponse(responseCode = "404", description = "角色未找到")
    })
    @PostMapping("/getById")
    public CharacterProfile getCharacterById(@RequestBody CharacterIdRequestDTO request) {
        return characterProfileService.getCharacterProfileById(request.getCharacterId());
    }

    /**
     * 更新角色信息
     * @param updateDTO 包含更新信息的角色DTO
     * @return 更新后的角色信息
     */
    @Operation(summary = "更新角色信息", description = "通过提供角色ID和更新字段更新角色信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "角色信息更新成功"),
            @ApiResponse(responseCode = "404", description = "角色未找到")
    })
    @PostMapping("/update")
    public CharacterProfile updateCharacter(@RequestBody CharacterProfileUpdateDTO updateDTO) {
        CharacterProfile characterProfile = characterProfileService.getCharacterProfileById(updateDTO.getCharacterId());

        // 根据DTO更新角色信息
        if (updateDTO.getName() != null) characterProfile.setName(updateDTO.getName());
        if (updateDTO.getAvatar() != null) characterProfile.setAvatar(updateDTO.getAvatar());
        // 更新其它字段...

        return characterProfileService.updateCharacterProfile(characterProfile);
    }

    /**
     * 删除角色
     * @param characterId 角色ID
     */
    @Operation(summary = "删除角色", description = "根据角色ID删除角色")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "角色删除成功"),
            @ApiResponse(responseCode = "404", description = "角色未找到")
    })
    @PostMapping("/delete")
    public void deleteCharacter(@RequestBody Long characterId) {
        characterProfileService.deleteCharacterProfile(characterId);
    }

    /**
     * 获取所有角色
     * @return 所有角色信息的列表
     */
    @Operation(summary = "获取所有角色", description = "获取所有角色信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取所有角色信息")
    })
    @PostMapping("/getAll")
    public List<CharacterProfile> getAllCharacters() {
        return characterProfileService.getAllCharacterProfiles();
    }

    // 更新角色势力
    @PostMapping("/updateFaction")
    public CharacterProfile updateFaction(@RequestBody CharacterProfileFactionUpdateDTO factionUpdateDTO) {
        CharacterProfile characterProfile = characterProfileService.getCharacterProfileById(factionUpdateDTO.getCharacterId());
        characterProfile.setFaction(factionUpdateDTO.getFaction());
        return characterProfileService.updateCharacterProfile(characterProfile);
    }

    // 获取角色的资源信息
    @PostMapping("/getResourceInfo")
    public CharacterProfileResourceInfoDTO getResourceInfo(@RequestBody Long characterId) {
        return characterProfileService.getResourceInfo(characterId);
    }

    // 更新角色的资源
    @PostMapping("/updateResources")
    public CharacterProfile updateResources(@RequestBody CharacterProfileResourceUpdateDTO resourceUpdateDTO) {
        CharacterProfile characterProfile = characterProfileService.getCharacterProfileById(resourceUpdateDTO.getCharacterId());
        // 更新资源字段
        if (resourceUpdateDTO.getYuanbao() >= 0) characterProfile.setYuanbao(resourceUpdateDTO.getYuanbao());
        if (resourceUpdateDTO.getCopperCoins() >= 0) characterProfile.setCopperCoins(resourceUpdateDTO.getCopperCoins());
        return characterProfileService.updateCharacterProfile(characterProfile);
    }
}

