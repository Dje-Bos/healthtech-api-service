package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MeasurementService {
    Map<LocalDate, List<MeasurementDto>> getGroupedByDate(Long userId, int page);

    List<MeasurementDto> getInTimeRange(Long userId, LocalDateTime start, LocalDateTime end);

    void removeAllByUser(User user);
}
