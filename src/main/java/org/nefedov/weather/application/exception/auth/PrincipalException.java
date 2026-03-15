package org.nefedov.weather.application.exception.auth;

import static org.nefedov.weather.application.exception.auth.AuthException.AuthExceptionMessages.LOGIN_OR_PASSWORD;

public class PrincipalException extends AuthException {

    public PrincipalException() {
        super(LOGIN_OR_PASSWORD.getMessage());
    }
}
