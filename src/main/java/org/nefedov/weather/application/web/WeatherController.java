package org.nefedov.weather.application.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.WeatherClientResponseDto;
import org.nefedov.weather.application.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{city}")
    public WeatherClientResponseDto findByCity(@PathVariable("city") String city) {
        return weatherService.findByCity(city).orElseThrow();
    }

    @GetMapping
    public List<WeatherClientResponseDto> findForUser(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        return weatherService.findForUser(id);
    }
}
