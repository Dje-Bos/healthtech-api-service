package com.boyarsky.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SeverityLevel {
    INFO,
    WARN,
    IMMEDIATE_REACTION;

    @JsonValue
    public String lowerCaseName() {
        return name().toLowerCase();
    }
}
