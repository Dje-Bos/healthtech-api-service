package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;
import com.boyarsky.apiservice.entity.MeasurementEntry;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.MeasurementService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.boyarsky.apiservice.mapper.MeasurementMapper.MEASUREMENT_MAPPER;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, List<MeasurementDto>> getGroupedByDate(Long userId, int page) {
        List<MeasurementEntry> foundMeasurements = measurementRepository.findByUserIdOrderByCreatedDateDesc(userId, PageRequest.of(page, 30));
        return foundMeasurements.stream()
                .map(MEASUREMENT_MAPPER::toDto)
                .collect(Collectors.groupingBy(measurementDto -> measurementDto.getCreated().toLocalDate(), LinkedHashMap::new, Collectors.toList()));
    }

    @Override
    @Transactional
    public MeasurementDto create(Long userId, CreateMeasurementRequest createMeasurement) {
        MeasurementEntry newMeasurement = MEASUREMENT_MAPPER.toEntity(createMeasurement);
        newMeasurement.setUser(userRepository.getUserById(userId));

        MeasurementEntry savedMeasurement = measurementRepository.save(newMeasurement);

        return MEASUREMENT_MAPPER.toDto(savedMeasurement);
    }
}
