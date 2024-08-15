package com.selimhocaoglu.linkmanager.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private String status;
    private T data;
    private String message;

    public ResponseDTO(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
