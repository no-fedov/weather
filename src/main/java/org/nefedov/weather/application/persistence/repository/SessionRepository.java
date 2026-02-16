package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.persistence.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
}
