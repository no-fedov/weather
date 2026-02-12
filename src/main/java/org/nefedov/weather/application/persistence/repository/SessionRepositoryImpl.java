package org.nefedov.weather.application.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.nefedov.weather.application.persistence.entity.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Session save(Session entity) {
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        session.flush();
        return entity;
    }

    @Override
    public Optional<Session> findById(UUID uuid) {
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        Session userSession = session.find(Session.class, uuid);
        return Optional.ofNullable(userSession);
    }
}
