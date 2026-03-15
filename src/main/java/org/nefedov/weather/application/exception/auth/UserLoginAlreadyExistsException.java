package org.nefedov.weather.application.exception.auth;

import static org.nefedov.weather.application.exception.auth.AuthException.AuthExceptionMessages.LOGIN_ALREADY_EXISTS;

public class UserLoginAlreadyExistsException extends AuthException {

    public UserLoginAlreadyExistsException() {
        super(LOGIN_ALREADY_EXISTS.getMessage());
    }
}
