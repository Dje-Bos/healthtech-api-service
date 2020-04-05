package com.boyarsky.apiservice.dto.measurement;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreatePressureDto extends CreateMeasurementDto {
    @NotNull
    @Min(10)
    @Max(300)
    private Integer systolic;

    @NotNull
    @Min(10)
    @Max(200)
    private Integer diastolic;
}
