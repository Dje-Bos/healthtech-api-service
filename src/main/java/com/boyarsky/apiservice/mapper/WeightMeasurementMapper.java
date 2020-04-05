package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateWeightDto;
import com.boyarsky.apiservice.entity.measurement.WeightMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = MeasurementConfig.class)
public interface WeightMeasurementMapper {
    WeightMeasurementMapper WEIGHT_MAPPER = Mappers.getMapper(WeightMeasurementMapper.class);

    @Mappings({
            @Mapping(source = "weight", target = "value"),
            @Mapping(target = "type", expression = "java(com.boyarsky.apiservice.entity.measurement.MeasurementType.WEIGHT)")
    })
    MeasurementDto toDto(WeightMeasurement entity);

    @Mappings({
            @Mapping(target = "weight"),
            @Mapping(target = "uid", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    WeightMeasurement create(CreateWeightDto newWeight);
}
