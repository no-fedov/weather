package util;

import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;

import java.time.LocalDateTime;

public class SessionUtil {

    public static Session getSessionWithoutUser() {
        return new Session(null, LocalDateTime.now(), null);
    }

    public static Session getSessionWithUser(User user) {
        return new Session(null, LocalDateTime.now(), user);
    }
}
