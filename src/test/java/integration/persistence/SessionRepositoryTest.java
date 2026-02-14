package integration.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.SessionRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.SessionUtil.getSessionWithUser;
import static util.SessionUtil.getSessionWithoutUser;
import static util.UserUtil.getUserWithoutId;

public class SessionRepositoryTest extends RepositoryTest {

    private static final String USER_LOGIN_TEST = "test_login";
    private static final String USER_PASSWORD_TEST = "test_password";

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveSessionWithoutUser_Unsuccessful() {
        Session session = getSessionWithoutUser();

        assertThrows(Exception.class, () -> sessionRepository.save(session));
    }

    @Test
    public void saveSessionWithUser_Successful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        userRepository.save(user);
        Session session = getSessionWithUser(user);

        Session savedSession = sessionRepository.save(session);

        assertEquals(user.getLogin(), savedSession.getUser().getLogin());
        assertEquals(user.getPassword(), savedSession.getUser().getPassword());
        assertEquals(session.getExpiresAt(), savedSession.getExpiresAt());
        assertNotNull(savedSession.getId());
    }
}
