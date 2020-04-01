package com.boyarsky.apiservice.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MeasurementType {
    PULSE,
    PRESSURE,
    TEMPERATURE,
    WEIGHT,
    GLUCOSE;

    @JsonValue
    public String lowerCaseName() {
        return name().toLowerCase();
    }

    @JsonCreator
    public MeasurementType createType(String type) {
        return MeasurementType.valueOf(type.toUpperCase());
    }
}
