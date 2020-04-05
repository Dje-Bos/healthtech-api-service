package com.boyarsky.apiservice.entity.measurement;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MeasurementUnit {
    KILOGRAM("kg"),
    CELSIUS_DEGREES("°C"),
    BEATS_PER_MINUTE("bpm"),
    MERCURY_MM("mmHG"),
    MMOL_PER_LITRE("mmol/L");

    private String unit;

    MeasurementUnit(String unit) {
        this.unit = unit;
    }

    @JsonValue
    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return unit;
    }
}
