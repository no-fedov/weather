package org.nefedov.weather.application.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nefedov.weather.application.dto.CoordinateDto;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.entity.User;
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

    private final SessionFactory sessionFactory;
    private final UserRepository userRepository;

    @Override
    public Location save(Location entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        session.flush();
        return entity;
    }

    // TODO: нужен ли этот метод?
    @Override
    public Optional<Location> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Location location = session.find(Location.class, id);
        return Optional.ofNullable(location);
    }

    // TODO: нужен ли этот метод?
    @Override
    public Optional<Location> findByCoordinate(CoordinateDto coordinate) {
        Session session = sessionFactory.getCurrentSession();
        Location location = session.createQuery(FIND_LOCATION_BY_COORDINATE_TEMPLATE, Location.class)
                .setParameter("latitude", coordinate.lat())
                .setParameter("longitude", coordinate.lon())
                .getSingleResult();
        return Optional.ofNullable(location);
    }

    @Override
    public Set<Location> findByUserId(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getLocations)
                .orElseThrow();
    }
}
