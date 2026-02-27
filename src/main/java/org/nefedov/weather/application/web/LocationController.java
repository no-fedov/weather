package org.nefedov.weather.application.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.service.LocationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public void add(LocationDto locationDto, HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        locationService.saveLocationForUser(locationDto, id);
    }

    @DeleteMapping
    public void deleteForUser(LocationDto locationDto, HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        locationService.deleteLocationForUser(locationDto, id);
    }
}
