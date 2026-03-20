package org.nefedov.weather.application.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;

@StandardException
public class AuthException extends RuntimeException {
    @Getter
    @RequiredArgsConstructor
    protected enum AuthExceptionMessages {

        LOGIN_ALREADY_EXISTS("Account with this login already exists!"),
        SESSION_EXPIRATION("Please sign-in again!"),
        LOGIN_OR_PASSWORD("Invalid username or password.");

        private final String message;
    }
}
