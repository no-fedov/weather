package org.nefedov.weather.application.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path = {"/sign-up", "/"})
@RequiredArgsConstructor
public class SessionController {

    private final RegistrationService registrationService;

    @GetMapping
    public String getRegistrationForm(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "sign-up";
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registration(UserCreateDto dto, HttpServletResponse response) {
        SessionDto registration = registrationService.registration(dto);
        Cookie cookie = new Cookie("session-id", registration.uuid().toString());
        response.addCookie(cookie);
    }
}
