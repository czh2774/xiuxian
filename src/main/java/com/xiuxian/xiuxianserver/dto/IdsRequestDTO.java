package com.xiuxian.xiuxianserver.dto;

import java.util.List;

/**
 * 用于批量获取道具信息的请求DTO
 */
public class IdsRequestDTO {

    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
