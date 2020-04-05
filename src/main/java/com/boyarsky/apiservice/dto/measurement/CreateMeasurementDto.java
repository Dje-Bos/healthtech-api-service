package com.boyarsky.apiservice.dto.measurement;

import com.boyarsky.apiservice.entity.measurement.MeasurementUnit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public abstract class CreateMeasurementDto {
    @NotNull
    private MeasurementUnit unit;
}
