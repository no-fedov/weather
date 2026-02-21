package org.nefedov.weather.application.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class SessionMapper {

    public SessionDto toDto(Session session) {
        return new SessionDto(session.getId(), session.getUser().getId(), session.getExpiresAt());
    }

    public Session toEntity(LocalDateTime expiresAt, User user) {
        Session session = new Session(expiresAt);
        session.setUser(user);
        return session;
    }
}
