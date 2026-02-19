package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.persistence.entity.Location;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    Optional<Location> findByCoordinate(Double latitude, Double longitude);
}
