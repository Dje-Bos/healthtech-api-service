package com.boyarsky.apiservice.dto;

import com.boyarsky.apiservice.entity.MeasurementType;
import com.boyarsky.apiservice.entity.MeasurementUnit;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class MeasurementDto {
    private UUID uid;

    private MeasurementType type;

    private OffsetDateTime created;

    private String value;

    private MeasurementUnit unit;
}
