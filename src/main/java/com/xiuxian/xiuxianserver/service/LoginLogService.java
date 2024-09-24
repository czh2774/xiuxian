package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.LoginLog;
import com.xiuxian.xiuxianserver.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService {

    @Autowired
    private LoginLogRepository loginLogRepository;

    // 保存登录日志
    public LoginLog saveLoginLog(LoginLog loginLog) {
        return loginLogRepository.save(loginLog);
    }
}
