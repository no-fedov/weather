package org.nefedov.weather.application.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.exception.auth.AuthException;
import org.nefedov.weather.application.service.SessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Component("authFilter")
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie sessionCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), "session-id"))
                .findFirst()
                .orElseThrow(AuthException::new);
        String sessionId = sessionCookie.getValue();
        UUID uuid = UUID.fromString(sessionId);
        SessionDto session = sessionManager.find(uuid);
        validateSession(session);
        request.setAttribute("session", session);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        if (servletPath.equals("/") || servletPath.equals("/sign-in") || servletPath.equals("/sign-up")) {
            return true;
        }
        return servletPath.startsWith("/css") || servletPath.startsWith("/js");
    }

    private void validateSession(SessionDto session) {
        if (session.expiresAt().isBefore(LocalDateTime.now())) {
            throw new AuthException();
        }
    }
}
