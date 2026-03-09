package org.nefedov.weather.application.dto;

import java.math.BigDecimal;

public record CoordinateDto(BigDecimal latitude, BigDecimal longitude) {
}
