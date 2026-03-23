package org.nefedov.weather.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.service.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/sign-out")
@RequiredArgsConstructor
public class LogoutController {

    private final SessionManager sessionManager;

    @DeleteMapping
    public String signout(@RequestAttribute("session") SessionDto session) {
        sessionManager.delete(session.uuid());
        log.info("The user with id = {} login = {} logout", session.userId(), session.userLogin());
        return "sign-in";
    }
}
