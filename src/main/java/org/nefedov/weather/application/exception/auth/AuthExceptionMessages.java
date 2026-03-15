package org.nefedov.weather.application.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionMessages {

    LOGIN_ALREADY_EXISTS("Account with this login already exists."),
    SESSION_EXPIRATION("Session has expired, please sign-in again!"),
    PASSWORDS_NOT_MATCHES("Passwords don't match.");

    private final String message;
}
