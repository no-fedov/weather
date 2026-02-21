package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.dto.CoordinateDto;
import org.nefedov.weather.application.dto.WeatherClientResponseDto;
import org.nefedov.weather.application.persistence.entity.Location;

import java.util.Optional;
import java.util.Set;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    Optional<Location> findByCoordinate(CoordinateDto coordinate);

    Set<Location> findByUserId(Integer userId);
}
