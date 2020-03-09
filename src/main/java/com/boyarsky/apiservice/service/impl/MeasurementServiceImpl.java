package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;
import com.boyarsky.apiservice.entity.MeasurementEntry;
import com.boyarsky.apiservice.entity.MeasurementType;
import com.boyarsky.apiservice.entity.MeasurementUnit;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.boyarsky.apiservice.mapper.MeasurementMapper.MEASUREMENT_MAPPER;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementDto> getForUser(Long userId, int page) {
        List<MeasurementEntry> foundMeasurements = measurementRepository.findByUserId(userId, PageRequest.of(page, 10));
        return foundMeasurements.stream()
                .map(MEASUREMENT_MAPPER::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MeasurementDto create(Long userId, CreateMeasurementRequest createRequest) {
        MeasurementEntry newMeasurement = new MeasurementEntry();
        newMeasurement.setUnit(MeasurementUnit.BEATS_PER_MINUTE);
        newMeasurement.setUser(userRepository.getUserById(userId));
        newMeasurement.setValue(createRequest.getValue());
        newMeasurement.setMeasurementType(createRequest.getType());
        MeasurementEntry savedMeasurement = measurementRepository.save(newMeasurement);
        return MEASUREMENT_MAPPER.toDto(savedMeasurement);
    }
}
