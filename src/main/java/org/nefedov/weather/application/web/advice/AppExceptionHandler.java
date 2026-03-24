package org.nefedov.weather.application.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.exception.SessionNotFoundException;
import org.nefedov.weather.application.exception.auth.AuthException;
import org.nefedov.weather.application.exception.auth.UserLoginAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({AuthException.class, SessionNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authHandleException(Exception authException, Model model) {
        model.addAttribute("global_exception", authException.getMessage());
        if (authException instanceof UserLoginAlreadyExistsException) {
            return "error/sign-up-with-errors";
        }
        return "error/sign-in-with-errors";
    }

    @ExceptionHandler
    public String exceptionHandler(Throwable exception) {
        log.error("Server exception", exception);
        return "error/error";
    }
}
