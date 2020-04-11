package com.boyarsky.apiservice.dto.measurement;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateWeightDto extends CreateMeasurementDto {
    @NotNull
    @Min(10)
    @Max(999)
    private Float weight;
}
