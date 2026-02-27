package org.nefedov.weather.application.service;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public void saveLocationForUser(LocationDto dto, Integer userId) {
        locationRepository.saveLocationForUser(dto, userId);
    }

    @Transactional
    public void deleteLocationForUser(LocationDto location, Integer userId) {
        locationRepository.deleteLocationForUser(location, userId);
    }
}
