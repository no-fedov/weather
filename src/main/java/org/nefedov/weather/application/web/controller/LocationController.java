package org.nefedov.weather.application.web.controller;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public String add(LocationDto locationDto,
                      @RequestParam("cityName") String cityName,
                      @RequestAttribute("session") SessionDto session) {
        locationService.saveForUser(locationDto, session.userId());
        String encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        return String.format("redirect:/home/location?name=%s", encodeCityName);
    }

    @DeleteMapping
    public String deleteForUser(LocationDto locationDto,
                                @RequestParam("cityName") String cityName,
                                @RequestAttribute("session") SessionDto session) {
        locationService.deleteForUser(locationDto, session.userId());
        String encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        return String.format("redirect:/home/location?name=%s", encodeCityName);
    }

    @DeleteMapping
    @RequestMapping("/{userLocationId}")
    public String deleteForUser(@PathVariable("locationId") Integer userLocationId,
                                @RequestAttribute("session") SessionDto session) {
        locationService.deleteForUser(userLocationId, session.userId());
        return "redirect:/home";
    }
}
