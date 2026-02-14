package org.nefedov.weather.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionDto(UUID uuid, Integer userId, LocalDateTime expiresAt) {
}
