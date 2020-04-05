package com.boyarsky.apiservice.dto.measurement;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateGlucoseDto extends CreateMeasurementDto {
    @NotNull
    @Min(1)
    @Max(100)
    private Float glucose;
}
