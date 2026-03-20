package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Transactional
    public SessionDto registration(UserCreateDto user, LocalDateTime expiresAt) {
        User savedUser = userRepository.save(new User(user.getLogin(), user.getPassword()));
        return sessionManager.create(savedUser.getId(), expiresAt);
    }
}
