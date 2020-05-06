package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.service.MeasurementService;
import com.boyarsky.apiservice.util.MeasurementUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;


    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, List<MeasurementDto>> getGroupedByDate(Long userId, int page) {
        List<Measurement> foundMeasurements = measurementRepository.findByUserIdOrderByCreatedTimeDesc(userId, PageRequest.of(page, 30));
        return foundMeasurements.stream()
                .map(MeasurementUtil::toDto)
                .collect(Collectors.groupingBy(measurementDto -> measurementDto.getCreated().toLocalDate(), LinkedHashMap::new, Collectors.toList()));
    }

    @Override
    public void removeAllByUser(User user) {
        measurementRepository.removeAllByUser(user);
    }

}
