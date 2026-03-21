package org.nefedov.weather.application.persistence.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.mapper.LocationMapper;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private static final String FIND_LOCATION_BY_COORDINATE_TEMPLATE = """
            from Location l
            where l.latitude = :latitude and l.longitude = :longitude
            """;
    private static final String DELETE_FROM_USER_LOCATION_TEMPLATE = """
            delete from user_location
            where user_id = :userId and location_id = :locationId
            """;

    private final SessionFactory sessionFactory;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;

    @Override
    public Location save(Location entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        session.flush();
        return entity;
    }

    @Override
    public Optional<Location> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Location location = session.find(Location.class, id);
        return Optional.ofNullable(location);
    }

    public Optional<Location> findByCoordinate(LocationDto dto) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_LOCATION_BY_COORDINATE_TEMPLATE, Location.class)
                .setParameter("latitude", dto.latitude())
                .setParameter("longitude", dto.longitude())
                .uniqueResultOptional();
    }

    @Override
    public void saveForUser(LocationDto dto, Integer userId) {
        Location location = findByCoordinate(dto).orElseGet(() -> save(locationMapper.toEntity(dto)));
        User user = userRepository.findById(userId).orElseThrow();
        user.addLocation(location);
    }

    @Override
    public void deleteForUser(LocationDto locationDto, Integer userId) {
        Location location = findByCoordinate(locationDto).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        user.getLocations().remove(location);
    }

    @Override
    public void deleteForUser(Integer locationId, Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createNativeQuery(DELETE_FROM_USER_LOCATION_TEMPLATE)
                .setParameter("locationId", locationId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public Set<LocationDto> findByUserId(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getLocations)
                .map(locationMapper::toCoordinates).orElseThrow();
    }
}
