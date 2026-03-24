package org.nefedov.weather.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
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
        log.info("The user with id = {} login = {} added city tracking for the lon = {} and lat = {}",
                session.userId(),
                session.userLogin(),
                locationDto.longitude(),
                locationDto.latitude());
        String encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        return String.format("redirect:/home/location?name=%s", encodeCityName);
    }

    @DeleteMapping
    public String deleteForUser(LocationDto locationDto,
                                @RequestParam("cityName") String cityName,
                                @RequestAttribute("session") SessionDto session) {
        locationService.deleteForUser(locationDto, session.userId());
        log.info("The user with id = {} login = {} deleted city tracking for the lon = {} and lat = {}",
                session.userId(),
                session.userLogin(),
                locationDto.longitude(),
                locationDto.latitude());
        String encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        return String.format("redirect:/home/location?name=%s", encodeCityName);
    }

    @DeleteMapping
    @RequestMapping("/weather/{userLocationId}")
    public String deleteForUser(@PathVariable("userLocationId") Integer userLocationId,
                                @RequestAttribute("session") SessionDto session) {
        locationService.deleteForUser(userLocationId, session.userId());
        log.info("The user with id = {} login = {} deleted weather tracking for user_location = {}",
                session.userId(),
                session.userLogin(),
                userLocationId);
        return "redirect:/home";
    }
}
