package org.nefedov.weather.application.persistence.mapper;

import org.nefedov.weather.application.dto.CoordinateDto;
import org.nefedov.weather.application.persistence.entity.Location;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class LocationMapper {

    public CoordinateDto toCoordinate(Location location) {
        return new CoordinateDto(location.getLatitude(), location.getLongitude());
    }

    public Set<CoordinateDto> toCoordinates(Collection<Location> locations) {
        return locations.stream()
                .map(this::toCoordinate)
                .collect(Collectors.toSet());
    }
}
