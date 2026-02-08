package util;

import org.nefedov.weather.persistence.entity.Session;
import org.nefedov.weather.persistence.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionUtil {

    public static Session getSessionWithoutUser() {
        return new Session(null, LocalDateTime.now(), null);
    }

    public static Session getSessionWithUser(User user) {
        return new Session(null, LocalDateTime.now(), user);
    }
}
