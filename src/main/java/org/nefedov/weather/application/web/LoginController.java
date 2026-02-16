package org.nefedov.weather.application.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.UserLoginDto;
import org.nefedov.weather.application.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/sign-in")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String signInForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "sign-in";
    }

    @PostMapping
    public void signIn(UserLoginDto dto, HttpServletResponse response) {
        SessionDto session = loginService.login(dto, LocalDateTime.now());
        Cookie cookie = new Cookie("session-id", session.uuid().toString());
        response.addCookie(cookie);
    }
}
