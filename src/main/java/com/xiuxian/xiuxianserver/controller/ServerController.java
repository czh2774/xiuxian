package com.xiuxian.xiuxianserver.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.xiuxian.xiuxianserver.entity.Server;
import com.xiuxian.xiuxianserver.service.ServerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 服务器控制层，提供服务器相关的 API 接口
 */
@Tag(name = "服务器管理", description = "服务器相关的 API 接口")
@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    /**
     * 获取所有服务器信息，按创建时间倒序排列
     *
     * @return 服务器列表
     */
    @Operation(summary = "获取所有服务器信息", description = "按创建时间倒序排列，返回所有服务器信息。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取服务器列表"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/list")
    public List<Server> getAllServers() {
        return serverService.getAllServers();
    }
}
