package org.nefedov.weather.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.WeatherClientResponseDto;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final UserRepository userRepository;
    private final WeatherClient weatherClient;

    public Optional<WeatherClientResponseDto> findByCity(String city) {
        return weatherClient.findByCityName(city);
    }

    public List<WeatherClientResponseDto> findForUser(Integer userId) {
        List<WeatherClientResponseDto> result = new LinkedList<>();
        User user = userRepository.findById(userId).orElseThrow();
        Set<Location> locations = user.getLocations();
        for (Location location : locations) {
            try {
                var weatherByCoordinate = weatherClient.findByCoordinate(location.getLatitude(), location.getLongitude());
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
