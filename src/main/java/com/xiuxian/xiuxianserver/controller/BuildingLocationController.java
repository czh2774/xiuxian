package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import com.xiuxian.xiuxianserver.service.BuildingLocationService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/building/locations")
public class BuildingLocationController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingLocationController.class);

    private final BuildingLocationService buildingLocationService;

    public BuildingLocationController(BuildingLocationService buildingLocationService) {
        this.buildingLocationService = buildingLocationService;
    }

    /**
     * 获取所有建筑位置模板数据
     *
     * @param request HttpServletRequest对象
     * @return 所有建筑位置模板的列表，封装在CustomApiResponse中
     */
    @PostMapping("/list")
    @Operation(summary = "获取所有建筑位置模板", description = "获取系统中所有建筑位置模板的信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回所有建筑位置模板"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public ResponseEntity<CustomApiResponse<List<BuildingLocationTemplate>>> getAllBuildingLocations(HttpServletRequest request) {
        logger.info("请求获取所有建筑位置模板，来源IP: {}", request.getRemoteAddr());

        List<BuildingLocationTemplate> locations;
        try {
            locations = buildingLocationService.getBuildingLocationTemplates();
            logger.info("成功获取建筑位置模板列表，共 {} 个位置模板", locations.size());
        } catch (Exception e) {
            logger.error("获取建筑位置模板列表时发生错误: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取建筑位置模板失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("获取建筑位置模板成功", locations, request.getRequestURI()));
    }
}
