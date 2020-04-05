package com.boyarsky.apiservice.dto.measurement;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateTempDto extends CreateMeasurementDto {
    @NotNull
    @Min(34)
    @Max(43)
    private Float temp;
}
