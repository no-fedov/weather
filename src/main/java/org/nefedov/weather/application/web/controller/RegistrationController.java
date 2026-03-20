package org.nefedov.weather.application.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@Controller
@RequestMapping(path = {"/sign-up", "/"})
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String form() {
        return "sign-up";
    }

    @PostMapping
    public String registration(@Valid UserCreateDto dto,
                                     HttpServletResponse response,
                                     RedirectAttributes redirectAttributes) {
        SessionDto registration = registrationService.registration(dto, LocalDateTime.now());
        Cookie cookie = new Cookie("session-id", registration.uuid().toString());
        response.addCookie(cookie);
        redirectAttributes.addAttribute("new_user", dto.getLogin());
        return "redirect:/home";
    }
}
