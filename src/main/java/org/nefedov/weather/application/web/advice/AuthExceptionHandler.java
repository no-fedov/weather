package org.nefedov.weather.application.web.advice;

import org.nefedov.weather.application.exception.auth.AuthException;
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
    public String handle(AuthException authException, Model model) {
        model.addAttribute("global_exception", authException.getMessage());
        if (authException instanceof UserLoginAlreadyExistsException) {
            return "error/sign-up-with-errors";
        }
        return "error/sign-in-with-errors";
    }
}
