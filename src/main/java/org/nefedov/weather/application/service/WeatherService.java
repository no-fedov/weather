package org.nefedov.weather.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.LocationResponseDto;
import org.nefedov.weather.application.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final LocationService locationService;
    private final WeatherClient weatherClient;

    public List<LocationResponseDto> findLocationByCity(String city) {
        return weatherClient.findLocationByCityName(city);
    }

    public List<WeatherResponseDto> findForUser(Integer userId) {
        List<WeatherResponseDto> result = new LinkedList<>();
        Set<LocationDto> locations = locationService.findUserLocation(userId);
        for (LocationDto location : locations) {
            try {
                var weatherByCoordinate = weatherClient.findByCoordinate(location.latitude(), location.longitude());
                result.add(weatherByCoordinate);
            } catch (Exception e) {
                log.warn("Ошибка weather client", e);
            }
        }
        return result;
    }
}
