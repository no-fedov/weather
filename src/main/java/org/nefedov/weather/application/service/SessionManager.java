package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.exception.SessionNotFoundException;
import org.nefedov.weather.application.exception.UserNotFoundException;
import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.mapper.SessionMapper;
import org.nefedov.weather.application.persistence.repository.SessionRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DurationFormat;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionManager {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionMapper sessionMapper;

    @DurationFormat(defaultUnit = DurationFormat.Unit.MINUTES, style = DurationFormat.Style.SIMPLE)
    @Value("${app.session.expiration}")
    private Duration expiration;

    @Transactional
    public SessionDto create(Integer userId) {
        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Session session = sessionMapper.toEntity(calculateExpiration(expiration), currentUser);
        sessionRepository.save(session);
        return sessionMapper.toDto(session);
    }

    @Transactional
    public SessionDto find(UUID uuid) {
        Session session = sessionRepository.findById(uuid).orElseThrow(SessionNotFoundException::new);
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
