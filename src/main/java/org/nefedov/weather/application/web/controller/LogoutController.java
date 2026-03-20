package org.nefedov.weather.application.web.controller;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.service.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-out")
@RequiredArgsConstructor
public class LogoutController {

    private final SessionManager sessionManager;

    @DeleteMapping
    public String signout(@RequestAttribute("session") SessionDto sessionDto) {
        sessionManager.delete(sessionDto.uuid());
        return "sign-in";
    }
}
