package com.boyarsky.apiservice.dto.measurement;

import com.boyarsky.apiservice.entity.measurement.MeasurementType;
import com.boyarsky.apiservice.entity.measurement.MeasurementUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MeasurementDto {
    private UUID uid;
    private MeasurementType type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private String value;
    private MeasurementUnit unit;
}
