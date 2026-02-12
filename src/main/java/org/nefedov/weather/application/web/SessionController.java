package org.nefedov.weather.application.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.application.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = {"/sign-up", "/"})
@RequiredArgsConstructor
public class SessionController {

    private final RegistrationService registrationService;

    @GetMapping
    public String signUpForm() {
        return "/static/sign-up";
    }

    @PostMapping
    public void registration(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        SessionDto registration = registrationService.registration(new UserCreateDto(login, password));
        Cookie cookie = new Cookie("Cookie", registration.uuid().toString());
        response.addCookie(cookie);
//        return registration.uuid().toString();
    }
}
