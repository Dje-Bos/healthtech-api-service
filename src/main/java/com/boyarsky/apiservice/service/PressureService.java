package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreatePressureDto;

public interface PressureService {
    MeasurementDto createForUser(CreatePressureDto newPressure, Long userId);
}
