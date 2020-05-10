package com.boyarsky.apiservice.controller.converter;

import com.boyarsky.apiservice.entity.measurement.MeasurementType;
import org.springframework.core.convert.converter.Converter;

public class String2MeasurementTypeConverter implements Converter<String, MeasurementType> {
    @Override
    public MeasurementType convert(String source) {
        return MeasurementType.valueOf(source.toUpperCase());
    }
}
