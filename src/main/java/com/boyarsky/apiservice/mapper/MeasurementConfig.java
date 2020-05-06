package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateMeasurementDto;
import com.boyarsky.apiservice.entity.measurement.Measurement;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Mappings;

@MapperConfig(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface MeasurementConfig {

    @Mappings({
            @Mapping(source = "createdTime", target = "created"),
            @Mapping(target = "unit")
    })
    MeasurementDto toDto(Measurement entity);

    @Mappings({
            @Mapping(source = "created", target = "createdTime"),
            @Mapping(target = "unit"),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    Measurement toEntity(MeasurementDto dto);

    @Mappings({
                    @Mapping(target = "unit")
            })
    Measurement create(CreateMeasurementDto entity);

}
