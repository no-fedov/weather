package org.nefedov.weather.application.web.advice;

import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class MethodArgumentExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validationHandler(MethodArgumentNotValidException ex, Model model) {
        List<ObjectError> errors = ex.getAllErrors();
        processError(errors, model);
        return switch (ex.getTarget()) {
            case UserCreateDto target -> "redirect:sign-up-with-errors";
            case UserLoginDto target -> "redirect:sign-in-with-errors";
            default -> "redirect:/error";
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
