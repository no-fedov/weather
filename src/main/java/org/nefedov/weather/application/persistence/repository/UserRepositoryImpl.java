package org.nefedov.weather.application.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nefedov.weather.application.persistence.entity.User;
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

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User u where u.login = :login", User.class)
                .setParameter("login", login)
                .getResultStream()
                .findFirst();
    }
}
