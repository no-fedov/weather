package integration.persistence;

import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.SessionRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.SessionUtil.getSessionWithUser;
import static util.SessionUtil.getSessionWithoutUser;
import static util.UserUtil.getUserWithoutId;

public class SessionRepositoryTest extends RepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveSessionWithoutUser_Unsuccessful() {
        LocalDateTime expiresAt = LocalDateTime.now();
        Session session = getSessionWithoutUser(expiresAt);

        assertThrows(Exception.class, () -> sessionRepository.save(session));
    }

    @Test
    public void saveSessionWithUser_Successful() {
        LocalDateTime expiresAt = LocalDateTime.now();
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        userRepository.save(user);
        Session session = getSessionWithUser(expiresAt,user);

        Session savedSession = sessionRepository.save(session);

        assertEquals(user.getLogin(), savedSession.getUser().getLogin());
        assertEquals(user.getPassword(), savedSession.getUser().getPassword());
        assertEquals(session.getExpiresAt(), savedSession.getExpiresAt());
        assertNotNull(savedSession.getId());
    }
}
