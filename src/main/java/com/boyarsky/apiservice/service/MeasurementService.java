package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MeasurementService {
    Map<LocalDate, List<MeasurementDto>> getGroupedByDate(Long userId, int page);
}
