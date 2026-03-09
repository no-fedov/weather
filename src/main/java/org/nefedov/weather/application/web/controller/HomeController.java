package org.nefedov.weather.application.web.controller;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationResponseDto;
import org.nefedov.weather.application.dto.LocationUserResponseDto;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.dto.WeatherResponseDto;
import org.nefedov.weather.application.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final WeatherService weatherService;

    @GetMapping
    public String home(Model model, @RequestAttribute("session") SessionDto session) {
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
