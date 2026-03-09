package org.nefedov.weather.application.web.advice;

import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.web.controller.HomeController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;

@ControllerAdvice(assignableTypes = HomeController.class)
public class LoginHandler {

    @ModelAttribute("userLogin")
    public String currentUserLogin(@RequestAttribute("session") SessionDto session) {
        return session.userLogin();
    }
}
