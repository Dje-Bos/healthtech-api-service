package com.boyarsky.apiservice.controller.v1.dto;

import com.boyarsky.apiservice.dto.MeasurementDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MeasurementsByDate {
    private LocalDate date;
    private List<MeasurementDto> measurements;
}
