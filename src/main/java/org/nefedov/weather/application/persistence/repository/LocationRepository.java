package org.nefedov.weather.application.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nefedov.weather.application.persistence.entity.Location;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepository implements CrudRepository<Location, Integer> {

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
}
