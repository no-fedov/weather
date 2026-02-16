package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.persistence.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}
