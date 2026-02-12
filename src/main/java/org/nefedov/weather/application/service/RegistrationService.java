package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.SessionRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public SessionDto registration(UserCreateDto user) {
        User savedUser = userRepository.save(new User(user.login(), user.password()));
        Session savedSession = sessionRepository.save(new Session(null, LocalDateTime.now().plusHours(1), savedUser));
        return new SessionDto(savedSession.getId());
    }
}
