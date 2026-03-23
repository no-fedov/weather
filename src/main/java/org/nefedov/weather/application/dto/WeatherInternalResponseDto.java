package org.nefedov.weather.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeatherInternalResponseDto {
    private WeatherExternalResponseDto weather;
    private Integer userLocationId;
}
