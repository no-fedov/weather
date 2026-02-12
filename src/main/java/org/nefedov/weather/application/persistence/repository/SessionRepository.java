package org.nefedov.weather.application.persistence.repository;

import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.repository.CrudRepository;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
}
