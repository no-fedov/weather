package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.mapper.SessionMapper;
import org.nefedov.weather.application.persistence.repository.SessionRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionManager {

    private final Duration sessionLifePeriod = Duration.ofMinutes(15);

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionMapper sessionMapper;

    @Transactional
    public SessionDto create(Integer userId) {
        User currentUser = userRepository.findById(userId).orElseThrow();
        Session session = sessionMapper.toEntity(calculateExpiration(sessionLifePeriod), currentUser);
        sessionRepository.save(session);
        return sessionMapper.toDto(session);
    }

    @Transactional
    public SessionDto find(UUID uuid) {
        Session session = sessionRepository.findById(uuid).orElseThrow();
        return sessionMapper.toDto(session);
    }

    @Transactional
    public void delete(UUID uuid) {
        sessionRepository.delete(uuid);
    }

    private LocalDateTime calculateExpiration(Duration lifePeriod) {
        return LocalDateTime.now().plus(lifePeriod);
    }
}
