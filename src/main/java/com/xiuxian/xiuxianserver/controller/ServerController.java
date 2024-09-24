package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.entity.Server;
import com.xiuxian.xiuxianserver.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 服务器控制层，提供服务器相关的 API 接口
 */
@RestController
@RequestMapping("/api/servers")
public class ServerController {

    @Autowired
    private ServerService serverService;

    /**
     * 获取所有服务器信息，按创建时间倒序排列
     *
     * @return 服务器列表
     */
    @GetMapping("/list")
    public List<Server> getAllServers() {
        return serverService.getAllServers();
    }

    // 其他方法...
}
