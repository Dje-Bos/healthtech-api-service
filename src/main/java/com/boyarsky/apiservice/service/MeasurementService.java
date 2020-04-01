package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MeasurementService {
    Map<LocalDate, List<MeasurementDto>> getGroupedByDate(Long userId, int page);

    MeasurementDto create(Long userId, CreateMeasurementRequest createRequest);
}
