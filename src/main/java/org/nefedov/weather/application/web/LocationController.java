package org.nefedov.weather.application.web;

import lombok.RequiredArgsConstructor;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.service.LocationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public void add(LocationDto locationDto,  @RequestAttribute("userId") Integer userId) {
        locationService.saveLocationForUser(locationDto, userId);
    }

    @DeleteMapping
    public void deleteForUser(LocationDto locationDto, @RequestAttribute("userId") Integer userId) {
        locationService.deleteLocationForUser(locationDto, userId);
    }
}
