package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Transactional
    public SessionDto login(UserLoginDto dto, LocalDateTime expiresAt) {
        User user = userRepository.findByLogin(dto.getLogin()).orElseThrow();
        if (!Objects.equals(user.getPassword(), dto.getPassword())) {
            throw new RuntimeException();
        }
        return sessionManager.create(user.getId(), expiresAt);
    }
}
