package com.boyarsky.apiservice.entity;

public enum MeasurementUnit {
    KILOGRAM("kg"),
    CELSIUS_DEGREES("°C"),
    BEATS_PER_MINUTE("bmp"),
    MERCURY_MM("mmHG"),
    MMOL_PER_LITRE("mmol/L");

    private String measurementUnit;
    MeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    @Override
    public String toString() {
        return measurementUnit;
    }
}
