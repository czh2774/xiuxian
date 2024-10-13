package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingStatusUpdateDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CharacterBuildingController 是用于处理角色建筑相关请求的控制器类。
 * 它提供了创建建筑、更新建筑状态、获取角色所有建筑的API接口。
 */
@RestController
@RequestMapping("/character/buildings")
public class CharacterBuildingController {

    private final CharacterBuildingService characterBuildingService;

    /**
     * 构造器注入 CharacterBuildingService。
     *
     * @param characterBuildingService 角色建筑服务对象
     */
    @Autowired
    public CharacterBuildingController(CharacterBuildingService characterBuildingService) {
        this.characterBuildingService = characterBuildingService;
    }

    /**
     * 创建角色建筑的API接口。
     * 该接口接收建筑创建请求的数据传输对象（DTO），并返回创建的建筑详细信息。
     *
     * @param createDTO 角色建筑创建请求的数据传输对象
     * @return 创建的角色建筑的详细信息
     */
    @Operation(summary = "Create a new character building", description = "创建一个新的角色建筑")
    @ApiResponse(responseCode = "201", description = "Building created successfully")
    @PostMapping("/create")
    public CharacterBuildingDTO createBuilding(@RequestBody CharacterBuildingCreateRequestDTO createDTO) {
        return characterBuildingService.createCharacterBuildingInstance(createDTO);
    }

    /**
     * 更新角色建筑状态的API接口。
     * 该接口接收状态更新请求的数据传输对象（DTO），并返回更新后的建筑详细信息。
     *
     * @param updateDTO 角色建筑状态更新请求的数据传输对象
     * @return 更新后的角色建筑的详细信息
     */
    @Operation(summary = "Update character building status", description = "更新角色建筑的状态")
    @ApiResponse(responseCode = "200", description = "Building status updated successfully")
    @PutMapping("/status")
    public CharacterBuildingDTO updateBuildingStatus(@RequestBody CharacterBuildingStatusUpdateDTO updateDTO) {
        characterBuildingService.updateCharacterBuildingStatus(updateDTO.getBuildingId(), updateDTO.getNewStatus());
        return characterBuildingService.getCharacterBuildingById(updateDTO.getBuildingId());
    }

    /**
     * 获取角色所有建筑的API接口。
     * 该接口根据角色ID返回该角色的所有建筑实例。
     *
     * @param characterId 角色ID
     * @return 角色的所有建筑实例的列表
     */
    @Operation(summary = "Get all character buildings", description = "获取角色所有的建筑")
    @ApiResponse(responseCode = "200", description = "List of character buildings retrieved successfully")
    @GetMapping("/{characterId}")
    public List<CharacterBuildingDTO> getAllBuildings(@PathVariable Long characterId) {
        return characterBuildingService.getCharacterBuildingsByCharacterId(characterId);
    }
}
