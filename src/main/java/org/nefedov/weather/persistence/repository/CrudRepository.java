package org.nefedov.weather.persistence.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudRepository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);
}
