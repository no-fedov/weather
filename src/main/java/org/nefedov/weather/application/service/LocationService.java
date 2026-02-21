package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.CoordinateDto;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.mapper.LocationMapper;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public Set<CoordinateDto> findByUserId(Integer userId) {
        Set<Location> locations = locationRepository.findByUserId(userId);
        return locationMapper.toCoordinates(locations);
    }
}
