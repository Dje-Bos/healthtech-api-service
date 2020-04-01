package com.boyarsky.apiservice.dto;

import com.boyarsky.apiservice.entity.MeasurementType;
import com.boyarsky.apiservice.entity.MeasurementUnit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateMeasurementRequest {
    @NotBlank
    private String value;
    @NotNull
    private MeasurementType type;
    @NotNull
    private MeasurementUnit unit;
}
