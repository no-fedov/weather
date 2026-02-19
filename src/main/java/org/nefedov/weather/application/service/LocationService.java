package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.CoordinateDto;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final UserRepository userRepository;

    @Transactional
    public void saveLocationForTracking(Integer userId, CoordinateDto coordinate) {
        userRepository.findById(userId);
    }
}
