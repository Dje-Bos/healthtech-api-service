package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateTempDto;

public interface TemperatureService {
    MeasurementDto createForUser(CreateTempDto newTemp, Long userId);
}
