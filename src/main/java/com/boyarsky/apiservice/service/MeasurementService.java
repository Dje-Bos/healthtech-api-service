package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;

import java.util.List;

public interface MeasurementService {
    List<MeasurementDto> getForUser(Long userId, int page);

    MeasurementDto create(Long userId, CreateMeasurementRequest createRequest);
}
