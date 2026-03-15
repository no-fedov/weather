package org.nefedov.weather.application.exception.auth;

import static org.nefedov.weather.application.exception.auth.AuthException.AuthExceptionMessages.SESSION_EXPIRATION;

public class SessionExpiredException extends AuthException {

    public SessionExpiredException() {
        super(SESSION_EXPIRATION.getMessage());
    }
}
