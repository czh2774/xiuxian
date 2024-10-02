package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.LoginLog;
import com.xiuxian.xiuxianserver.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务层，处理与登录日志相关的业务逻辑。
 */
@Service
public class LoginLogService {

    private final LoginLogRepository loginLogRepository;

    @Autowired
    public LoginLogService(LoginLogRepository loginLogRepository) {
        this.loginLogRepository = loginLogRepository;
    }

    /**
     * 保存登录日志。
     * @param loginLog 登录日志对象
     * @return 保存后的登录日志对象
     */
    public LoginLog saveLoginLog(LoginLog loginLog) {
        return loginLogRepository.save(loginLog);  // 可能抛出数据库异常
    }
}
