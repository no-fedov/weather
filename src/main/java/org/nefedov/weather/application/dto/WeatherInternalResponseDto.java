package org.nefedov.weather.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherInternalResponseDto {
    private WeatherExternalResponseDto weather;
    private Integer userLocationId;
}
