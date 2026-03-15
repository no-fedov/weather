package org.nefedov.weather.application.web.advice;

import org.nefedov.weather.application.exception.auth.AuthException;
import org.nefedov.weather.application.exception.auth.PasswordNotMatches;
import org.nefedov.weather.application.exception.auth.SessionExpiredException;
import org.nefedov.weather.application.exception.auth.UserLoginAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authHandle(AuthException authException, Model model) {
        model.addAttribute("error", authException.getMessage());
        // TODO: что установить в модель?
        switch(authException) {
            case PasswordNotMatches _:

            case UserLoginAlreadyExistsException _:
                break;
            case SessionExpiredException _:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + authException);
        }
        return null;
    }
}
