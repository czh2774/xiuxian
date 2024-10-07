package com.xiuxian.xiuxianserver.controller;

import io.swagger.v3.oas.annotations.media.Schema;

public class BuildingTemplateRequest {
    @Schema(description = "建筑模板 ID", example = "1")
    private Long id;

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
