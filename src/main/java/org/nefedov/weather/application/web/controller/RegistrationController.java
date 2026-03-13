package org.nefedov.weather.application.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registration(@Valid UserCreateDto dto,
                                     HttpServletResponse response,
                                     RedirectAttributes redirectAttributes) {
        SessionDto registration = authService.registration(dto);
        Cookie cookie = new Cookie("session-id", registration.uuid().toString());
        response.addCookie(cookie);
        redirectAttributes.addAttribute("new_user", dto.getLogin());
        return "redirect:/home";
    }
}
