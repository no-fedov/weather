package org.nefedov.weather.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final LocationService locationService;
    private final WeatherClient weatherClient;

    public List<LocationUserResponseDto> findLocationByCity(Integer userId, String city) {
        Set<CoordinateDto> addedUserCoordinate = locationService.findUserLocation(userId).stream()
                .map(e -> new CoordinateDto(e.latitude(), e.longitude()))
                .collect(Collectors.toSet());
        return weatherClient.findLocationByCityName(city).stream()
                .map(e -> convertToLocationUser(addedUserCoordinate, e)).toList();
    }

    public List<WeatherInternalResponseDto> findForUser(Integer userId) {
        List<WeatherInternalResponseDto> result = new LinkedList<>();
        Set<LocationDto> locations = locationService.findUserLocation(userId);
        for (LocationDto location : locations) {
            try {
                var weatherByCoordinate = weatherClient.findByCoordinate(location.latitude(), location.longitude());
                weatherByCoordinate.setCityName(location.name());
                WeatherInternalResponseDto weather = new WeatherInternalResponseDto(weatherByCoordinate, location.id());
                result.add(weather);
            } catch (Exception e) {
                log.warn("Ошибка weather client", e);
            }
        }
        return result;
    }

    private LocationUserResponseDto convertToLocationUser(Set<CoordinateDto> userCoordinates,
                                                          LocationResponseDto location) {
        CoordinateDto coordinateDto = new CoordinateDto(location.getLatitude(), location.getLongitude());
        LocationUserResponseDto locationUserResponseDto = new LocationUserResponseDto(location.getName(),
                location.getState(), location.getCountry(), location.getLatitude(), location.getLongitude(), null);
        locationUserResponseDto.setIsAdded(userCoordinates.contains(coordinateDto));
        return locationUserResponseDto;
    }
}
