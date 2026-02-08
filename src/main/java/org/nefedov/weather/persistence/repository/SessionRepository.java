package org.nefedov.weather.persistence.repository;

import org.nefedov.weather.persistence.entity.Session;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
}
