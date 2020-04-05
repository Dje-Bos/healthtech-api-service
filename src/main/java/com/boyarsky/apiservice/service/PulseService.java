package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreatePulseDto;

public interface PulseService {
    MeasurementDto createForUser(CreatePulseDto pulse, Long userId);
}
