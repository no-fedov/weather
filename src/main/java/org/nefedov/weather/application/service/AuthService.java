package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.nefedov.weather.application.exception.AuthException;
import org.nefedov.weather.application.exception.UserNotFoundException;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Transactional
    public SessionDto login(UserLoginDto dto) {
        User user = userRepository.findByLogin(dto.getLogin()).orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(user.getPassword(), dto.getPassword())) {
            throw new AuthException();
        }
        return sessionManager.create(user.getId());
    }

    @Transactional
    public SessionDto registration(UserCreateDto user) {
        User savedUser = userRepository.save(new User(user.getLogin(), user.getPassword()));
        return sessionManager.create(savedUser.getId());
    }
}
