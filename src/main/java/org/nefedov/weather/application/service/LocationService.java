package org.nefedov.weather.application.service;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public void saveForUser(LocationDto dto, Integer userId) {
        locationRepository.saveForUser(dto, userId);
    }

    @Transactional
    public void deleteForUser(LocationDto dto, Integer userId) {
        locationRepository.deleteForUser(dto, userId);
    }

    @Transactional
    public void deleteForUser(Integer locationId, Integer userId) {
        locationRepository.deleteForUser(locationId, userId);
    }

    @Transactional
    public Set<LocationDto> findUserLocation(Integer userId) {
       return locationRepository.findByUserId(userId);
    }
}
