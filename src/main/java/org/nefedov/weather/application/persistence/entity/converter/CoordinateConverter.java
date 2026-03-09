package org.nefedov.weather.application.persistence.entity.converter;

import jakarta.persistence.AttributeConverter;

import java.math.BigDecimal;

public class CoordinateConverter implements AttributeConverter<BigDecimal, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(BigDecimal attribute) {
        return attribute;
    }

    @Override
    public BigDecimal convertToEntityAttribute(BigDecimal dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData.stripTrailingZeros();
    }
}
