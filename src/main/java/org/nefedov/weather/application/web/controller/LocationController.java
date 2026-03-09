package org.nefedov.weather.application.web.controller;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public String add(LocationDto locationDto, @RequestParam("cityName") String cityName,
                      @RequestAttribute("session") SessionDto session) {
        locationService.saveLocationForUser(locationDto, session.userId());
        String encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        return String.format("redirect:/home/location?name=%s", encodeCityName);
    }

    @DeleteMapping
    public String deleteForUser(LocationDto locationDto, @RequestParam("cityName") String cityName,
                                @RequestAttribute("session") SessionDto session) {
        locationService.deleteLocationForUser(locationDto, session.userId());
        String encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        return String.format("redirect:/home/location?name=%s", encodeCityName);
    }
}
