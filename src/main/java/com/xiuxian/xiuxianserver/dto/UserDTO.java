package com.xiuxian.xiuxianserver.dto;

public class UserDTO {
    private Long platformUserId;
    private String name;
    private String loginType;
    private Long playId;  // 如果是由前端传递，可以包含 playId，否则在后端生成

    // Getters and Setters
    public Long getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Long getPlayId() {
        return playId;
    }

    public void setPlayId(Long playId) {
        this.playId = playId;
    }
}
