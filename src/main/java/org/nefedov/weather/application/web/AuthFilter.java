package org.nefedov.weather.application.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.service.SessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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
                .findFirst().orElseThrow();
        String sessionId = sessionCookie.getValue();
        UUID uuid = UUID.fromString(sessionId);
        SessionDto sessionDto = sessionManager.find(uuid);
        request.setAttribute("userId", sessionDto.userId());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldNotFilter = false;
        String servletPath = request.getServletPath();
        switch (servletPath) {
            case "/", "/sign-in", "/sign-up" -> shouldNotFilter = true ;
        };
        return shouldNotFilter;
    }
}
