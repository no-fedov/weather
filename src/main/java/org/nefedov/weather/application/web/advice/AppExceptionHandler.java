package org.nefedov.weather.application.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.nefedov.weather.application.exception.SessionNotFoundException;
import org.nefedov.weather.application.exception.auth.AuthException;
import org.nefedov.weather.application.exception.auth.UserLoginAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validationHandler(MethodArgumentNotValidException ex, Model model) {
        List<ObjectError> errors = ex.getAllErrors();
        processError(errors, model);
        return switch (ex.getTarget()) {
            case UserCreateDto target -> "error/sign-up-with-errors";
            case UserLoginDto target -> "error/sign-in-with-errors";
            default -> "error/error";
        };
    }

    private <T extends ObjectError> void processError(List<T> errors, Model model) {
        for (T error : errors) {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField().concat("_exception");
                model.addAttribute(fieldName, error.getDefaultMessage());
            } else {
                model.addAttribute("global_exception", error.getDefaultMessage());
            }
        }
    }
}
