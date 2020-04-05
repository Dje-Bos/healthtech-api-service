package com.boyarsky.apiservice.dto.measurement;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreatePulseDto extends CreateMeasurementDto {
    @NotNull
    @Min(10)
    @Max(999)
    private Integer pulse;
}
