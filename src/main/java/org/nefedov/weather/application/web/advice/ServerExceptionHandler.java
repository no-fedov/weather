package org.nefedov.weather.application.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler
    public String exceptionHandler(Throwable exception) {
        log.error("Server exception", exception);
        return "error/error";
    }
}
