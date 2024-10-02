package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.Server;
import com.xiuxian.xiuxianserver.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * 服务层，处理与服务器相关的业务逻辑。
 */
@Service
public class ServerService {

    private final ServerRepository serverRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * 获取所有服务器。
     * @return 服务器列表
     */
    public List<Server> getAllServers() {
        return serverRepository.findAll();  // 可能抛出数据库异常
    }

    /**
     * 根据服务器 ID 选择服务器。
     * @param id 服务器 ID
     * @return 选中的服务器，如果不存在则返回空
     */
    public Optional<Server> selectServer(Long id) {
        return serverRepository.findById(id);  // 可能抛出数据库异常
    }
}
