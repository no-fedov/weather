package org.nefedov.weather.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.WeatherClientResponseDto;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final LocationService locationService;
    private final WeatherClient weatherClient;

    public Optional<WeatherClientResponseDto> findByCity(String city) {
        return weatherClient.findByCityName(city);
    }

    public List<WeatherClientResponseDto> findForUser(Integer userId) {
        List<WeatherClientResponseDto> result = new LinkedList<>();
        Set<LocationDto> locations = locationService.findUserLocation(userId);
        for (LocationDto location : locations) {
            try {
                var weatherByCoordinate = weatherClient.findByCoordinate(location.lat(), location.lon());
                if (weatherByCoordinate.isPresent()) {
                    result.add(weatherByCoordinate.orElseThrow());
                }
            } catch (Exception e) {
                log.warn("Ошибка weather client", e);
            }
        }
        return result;
    }
}
