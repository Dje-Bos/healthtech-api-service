package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreatePressureDto;
import com.boyarsky.apiservice.entity.measurement.PressureMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = MeasurementConfig.class)
public interface PressureMeasurementMapper {
    PressureMeasurementMapper PRESSURE_MAPPER = Mappers.getMapper(PressureMeasurementMapper.class);

    @Mappings({
            @Mapping(target = "value", expression = "java(entity.getSystolic() + \"/\" + entity.getDiastolic())"),
            @Mapping(target = "type", expression = "java(com.boyarsky.apiservice.entity.measurement.MeasurementType.PRESSURE)")
    })
    MeasurementDto toDto(PressureMeasurement entity);

    @Mappings({
            @Mapping(target = "systolic", expression = "java(Integer.valueOf(dto.getValue().substring(0, dto.getValue().indexOf(\"/\"))))"),
            @Mapping(target = "diastolic", expression = "java(Integer.valueOf(dto.getValue().substring(dto.getValue().indexOf(\"/\")) + 1))")
    })
    PressureMeasurement toEntity(MeasurementDto dto);

    @Mappings({
            @Mapping(target = "systolic"),
            @Mapping(target = "diastolic"),
            @Mapping(target = "uid", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    PressureMeasurement create(CreatePressureDto newPressure);
}
