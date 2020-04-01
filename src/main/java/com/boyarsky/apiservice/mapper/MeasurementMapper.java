package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;
import com.boyarsky.apiservice.entity.MeasurementEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeasurementMapper {
    MeasurementMapper MEASUREMENT_MAPPER = Mappers.getMapper( MeasurementMapper.class );

    @Mappings({
            @Mapping(target = "type", source = "measurementType"),
            @Mapping(target = "value"),
            @Mapping(source = "createdDate", target = "created"),
            @Mapping(target = "unit")
    })
    MeasurementDto toDto(MeasurementEntry entity);

    @Mappings({
            @Mapping(source = "type", target = "measurementType"),
            @Mapping(target = "value"),
            @Mapping(target = "unit")
    })
    MeasurementEntry toEntity(CreateMeasurementRequest request);
}
