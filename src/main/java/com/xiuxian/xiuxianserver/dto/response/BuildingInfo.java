package com.xiuxian.xiuxianserver.dto.response;

import lombok.Data;

@Data
public class BuildingInfo {
    private Long id;
    private Long templateId;
    private Integer level;
    private String status;
    private Long locationId;
    private boolean isNew;
} 