package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.service.ExportImportService;
import com.boyarsky.apiservice.service.UserService;
import com.boyarsky.apiservice.util.MeasurementUtil;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportImportServiceImpl  implements ExportImportService {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final MeasurementRepository measurementRepository;

    public ExportImportServiceImpl(UserService userService, ObjectMapper objectMapper, MeasurementRepository measurementRepository) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.measurementRepository = measurementRepository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Resource exportAllMeasurements(Long userId) {
        User user = userService.getUserById(userId);
        List<MeasurementDto> allMeasurements = measurementRepository.findAllByUser(user)
                .stream()
                .map(MeasurementUtil::toDto)
                .collect(Collectors.toList());
        return new ByteArrayResource(objectMapper.writeValueAsBytes(allMeasurements));
    }

    @Transactional
    @SneakyThrows
    @Override
    public void importMeasurements(Long userId, MultipartFile file) {
        User user = userService.getUserById(userId);
        MappingIterator<MeasurementDto> measurementsIterator = objectMapper.readerFor(MeasurementDto.class).readValues(file.getInputStream());
        List<MeasurementDto> measurements = new ArrayList<>();
        while (measurementsIterator.hasNext()) {
            measurements.add(measurementsIterator.next());
        }
        List<Measurement> measurementEntities = measurements.stream().map(MeasurementUtil::toEntity).peek(entity -> entity.setUser(user)).collect(Collectors.toList());
        measurementRepository.saveAll(measurementEntities);
    }
}
