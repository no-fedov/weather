package org.nefedov.weather.application.web.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.exception.auth.AuthException;
import org.nefedov.weather.application.exception.auth.SessionExpiredException;
import org.nefedov.weather.application.service.SessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] sessionCookies = request.getCookies();
        if (Objects.isNull(sessionCookies)) {
            throw new AuthException();
        }
        Cookie authCookie = Arrays.stream(sessionCookies)
                .filter(cookie -> Objects.equals(cookie.getName(), "session-id"))
                .findFirst()
                .orElseThrow(AuthException::new);
        String sessionId = authCookie.getValue();
        UUID uuid = UUID.fromString(sessionId);
        SessionDto session = sessionManager.find(uuid);
        validateSession(session);
        request.setAttribute("session", session);
        return true;
    }

    private void validateSession(SessionDto session) {
        if (session.expiresAt().isBefore(LocalDateTime.now())) {
            throw new SessionExpiredException();
        }
    }
}
