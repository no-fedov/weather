package org.nefedov.weather.application.persistence.mapper;

import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.persistence.entity.Location;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public Location toEntity(LocationDto dto) {
        return new Location(dto.name(), dto.latitude(), dto.longitude());
    }

    public LocationDto toCoordinate(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude());
    }

    public Set<LocationDto> toCoordinates(Collection<Location> locations) {
        return locations.stream()
                .map(this::toCoordinate)
                .collect(Collectors.toSet());
    }
}
