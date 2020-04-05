package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateWeightDto;

public interface WeightService {
    MeasurementDto createForUser(CreateWeightDto newWeight, Long userId);
}
