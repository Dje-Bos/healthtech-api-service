package com.boyarsky.apiservice.dto;

import com.boyarsky.apiservice.entity.MeasurementType;
import lombok.Data;

@Data
public class CreateMeasurementRequest {
    private String value;
    private MeasurementType type;
}
