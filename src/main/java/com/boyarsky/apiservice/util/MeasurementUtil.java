package com.boyarsky.apiservice.util;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.entity.measurement.GlucoseMeasurement;
import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.entity.measurement.PressureMeasurement;
import com.boyarsky.apiservice.entity.measurement.PulseMeasurement;
import com.boyarsky.apiservice.entity.measurement.TempMeasurement;
import com.boyarsky.apiservice.entity.measurement.WeightMeasurement;

import static com.boyarsky.apiservice.mapper.GlucoseMeasurementMapper.GLUCOSE_MAPPER;
import static com.boyarsky.apiservice.mapper.PressureMeasurementMapper.PRESSURE_MAPPER;
import static com.boyarsky.apiservice.mapper.PulseMeasurementMapper.PULSE_MAPPER;
import static com.boyarsky.apiservice.mapper.TempMeasurementMapper.TEMP_MAPPER;
import static com.boyarsky.apiservice.mapper.WeightMeasurementMapper.WEIGHT_MAPPER;
import static java.lang.String.format;

public class MeasurementUtil {
    private MeasurementUtil() {
    }

    public static MeasurementDto toDto(Measurement measurement) {
        MeasurementDto measurementDto;
        if (measurement instanceof PulseMeasurement) {
            measurementDto = PULSE_MAPPER.toDto((PulseMeasurement) measurement);
        } else if (measurement instanceof GlucoseMeasurement) {
            measurementDto = GLUCOSE_MAPPER.toDto((GlucoseMeasurement) measurement);
        } else if (measurement instanceof PressureMeasurement) {
            measurementDto = PRESSURE_MAPPER.toDto((PressureMeasurement) measurement);
        } else if (measurement instanceof WeightMeasurement) {
            measurementDto = WEIGHT_MAPPER.toDto((WeightMeasurement) measurement);
        } else if (measurement instanceof TempMeasurement) {
            measurementDto = TEMP_MAPPER.toDto((TempMeasurement) measurement);
        } else {
            throw new IllegalStateException(format("Measurement with class '%s' cannot be converted to DTO. Consider adding additional org.mapstruct.Mapper", measurement.getClass()));
        }
        return measurementDto;
    }
}
