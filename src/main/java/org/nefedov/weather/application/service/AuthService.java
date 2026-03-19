package org.nefedov.weather.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.nefedov.weather.application.exception.UserNotFoundException;
import org.nefedov.weather.application.exception.auth.PrincipalException;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;
    private final PasswordProcessor passwordProcessor;

    @Transactional
    public SessionDto login(UserLoginDto user) {
        User foundUser = userRepository.findByLogin(user.getLogin()).orElseThrow(UserNotFoundException::new);
        if (!passwordProcessor.verify(user.getPassword(), foundUser.getPassword())) {
            throw new PrincipalException();
        }
        return sessionManager.create(foundUser.getId());
    }

    @Transactional
    public SessionDto registration(UserCreateDto user) {
        String hashedPassword = passwordProcessor.encode(user.getPassword());
        User savedUser = userRepository.save(new User(user.getLogin(), hashedPassword));
        return sessionManager.create(savedUser.getId());
    }
}
