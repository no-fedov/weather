package org.nefedov.weather.application.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nefedov.weather.application.persistence.entity.Location;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private static final String FIND_LOCATION_BY_COORDINATE_TEMPLATE = """
            from Location l
            where l.latitude = :latitude and l.longitude = :longitude
            """;

    private final SessionFactory sessionFactory;

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

    @Override
    public Optional<Location> findByCoordinate(Double latitude, Double longitude) {
        Session session = sessionFactory.getCurrentSession();
        Location location = session.createQuery(FIND_LOCATION_BY_COORDINATE_TEMPLATE, Location.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getSingleResult();
        return Optional.ofNullable(location);
    }
}
