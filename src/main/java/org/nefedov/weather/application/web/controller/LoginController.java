package org.nefedov.weather.application.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.nefedov.weather.application.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/sign-in")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @GetMapping
    public String form() {
        return "sign-in";
    }

    @PostMapping
    public String signIn(@Valid UserLoginDto dto, HttpServletResponse response) {
        SessionDto session = authService.login(dto);
        Cookie cookie = new Cookie("session-id", session.uuid().toString());
        response.addCookie(cookie);
        log.info("The user with id = {} login = {} sign-in", session.userId(), session.userLogin());
        return "redirect:home";
    }
}
