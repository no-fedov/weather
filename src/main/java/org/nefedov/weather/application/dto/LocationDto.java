package org.nefedov.weather.application.dto;

import java.math.BigDecimal;

public record LocationDto(Integer id, String name, BigDecimal latitude, BigDecimal longitude) {
}
