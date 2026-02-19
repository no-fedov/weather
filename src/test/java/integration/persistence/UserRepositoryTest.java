package integration.persistence;

import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.UserUtil.getUserWithoutId;

public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser_Successful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);

        User savedUser = userRepository.save(user);

        assertNotNull(user.getId());
        assertEquals(user.getLogin(), savedUser.getLogin());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void findUser_Successful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        User savedUser = userRepository.save(user);

        User findUser = userRepository.findById(user.getId()).orElseThrow();

        assertEquals(savedUser.getId(), findUser.getId());
        assertEquals(user.getLogin(), findUser.getLogin());
        assertEquals(savedUser.getPassword(), findUser.getPassword());
    }

    @Test
    public void saveUserWithDuplicateLogin_Unsuccessful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        User duplicateUser = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        userRepository.save(user);

        assertThrows(Exception.class, () ->  userRepository.save(duplicateUser));
    }
}
