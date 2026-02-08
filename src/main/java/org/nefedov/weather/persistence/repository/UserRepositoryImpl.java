package org.nefedov.weather.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nefedov.weather.persistence.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Override
    public User save(User entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        session.flush();
        return entity;
    }

    @Override
    public Optional<User> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.find(User.class, id);
        return Optional.ofNullable(user);
    }
}
