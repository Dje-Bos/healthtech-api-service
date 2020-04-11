package com.boyarsky.apiservice.dto;

import lombok.Data;

@Data
public class ApiResponseDto {
    private boolean success;
    private String message;

    public ApiResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
