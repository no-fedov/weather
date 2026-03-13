package org.nefedov.weather.application.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationUserResponseDto;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.WeatherResponseDto;
import org.nefedov.weather.application.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final WeatherService weatherService;

    @GetMapping
    public String home(Model model,
                       @RequestAttribute("session") SessionDto session,
                       @RequestParam("new_user") Optional<String> newUser,
                       HttpServletResponse response) {
        newUser.ifPresent(param -> response.setStatus(HttpStatus.CREATED.value()));
        List<WeatherResponseDto> weathers = weatherService.findForUser(session.userId());
        model.addAttribute("weathers", weathers);
        return "home";
    }

    @GetMapping("/location")
    public String location(Model model,
                           @RequestAttribute("session") SessionDto session,
                           @RequestParam("name") String cityName) {
        List<LocationUserResponseDto> locations = weatherService.findLocationByCity(session.userId(), cityName);
        model.addAttribute("locations", locations);
        model.addAttribute("cityName", cityName);
        return "search-results";
    }
}
