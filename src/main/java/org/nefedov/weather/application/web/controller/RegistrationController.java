package org.nefedov.weather.application.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping(path = {"/sign-up", "/"})
@RequiredArgsConstructor
public class RegistrationController {

    private final AuthService authService;

    @GetMapping
    public String form() {
        return "sign-up";
    }

    @PostMapping
    public String registration(@Valid UserCreateDto user,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {
        SessionDto session = authService.registration(user);
        Cookie cookie = new Cookie("session-id", session.uuid().toString());
        response.addCookie(cookie);
        redirectAttributes.addAttribute("new_user", user.getLogin());
        log.info("A new user with id = {} login = {} has been registered", session.userId(), session.userLogin());
        return "redirect:/home";
    }
}
