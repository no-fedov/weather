package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.persistence.entity.Location;

import java.util.Set;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    void saveLocationForUser(LocationDto dto, Integer userId);

    void deleteLocationForUser(LocationDto dto, Integer userId);

    Set<LocationDto> findByUserId(Integer userId);
}
