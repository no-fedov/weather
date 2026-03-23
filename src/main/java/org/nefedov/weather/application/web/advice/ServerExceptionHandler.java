package org.nefedov.weather.application.web.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler
    public String exceptionHandler(Throwable exception) {
        return "redirect:error/error";
    }
}
