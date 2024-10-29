package com.xiuxian.xiuxianserver.dto;

public class BoolResponseDTO {
    private boolean data; // data字段通用封装为bool

    public BoolResponseDTO(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
