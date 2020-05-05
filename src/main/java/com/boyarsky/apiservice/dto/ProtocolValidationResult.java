package com.boyarsky.apiservice.dto;

import lombok.Data;

@Data
public class ProtocolValidationResult {
    private String description;
    private ProtocolValidationStatus status;
    private String reminder;
    private String protocol;
}
