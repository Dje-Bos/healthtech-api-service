package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateGlucoseDto;

public interface GlucoseService {
    MeasurementDto createForUser(CreateGlucoseDto newGlucose, Long userId);
}
