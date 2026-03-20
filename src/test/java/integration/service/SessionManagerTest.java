package integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nefedov.weather.application.dto.SessionDto;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.nefedov.weather.application.service.SessionManager;
import org.nefedov.weather.config.app.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import util.UserUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {AppConfig.class})
@Transactional
public class SessionManagerTest {

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createSession_Successful() {
        User user = UserUtil.getUserWithoutId("login", "password");
        userRepository.save(user);
        LocalDateTime expiresAt = LocalDateTime.now();

        SessionDto createdSession = sessionManager.create(user.getId(), expiresAt);

        assertEquals(user.getId(), createdSession.userId());
        assertEquals(expiresAt, createdSession.expiresAt());
        assertNotNull(createdSession.uuid());
    }

    @Test
    public void createSessionWithNotExistUser_Unsuccessful() {
        User user = UserUtil.getUserWithId(1, "login", "password");
        LocalDateTime expiresAt = LocalDateTime.now();

        assertThrows(Exception.class, () -> sessionManager.create(user.getId(), expiresAt));
    }
}
