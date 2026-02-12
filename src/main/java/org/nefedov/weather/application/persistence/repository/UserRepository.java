package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
