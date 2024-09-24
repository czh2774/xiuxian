package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.Server;
import com.xiuxian.xiuxianserver.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务器服务类，负责处理服务器相关的业务逻辑
 */
@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    /**
     * 获取所有服务器，按创建时间倒序排列
     *
     * @return 所有服务器的列表
     */
    public List<Server> getAllServers() {
        return serverRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * 通过ID选择服务器
     *
     * @param serverId 服务器ID
     * @return 服务器信息
     */
    public Server selectServerById(Long serverId) {
        return serverRepository.findById(serverId)
                .orElseThrow(() -> new IllegalArgumentException("服务器未找到: " + serverId));
    }
}
