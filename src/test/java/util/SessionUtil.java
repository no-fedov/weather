package util;

import org.nefedov.weather.application.persistence.entity.Session;
import org.nefedov.weather.application.persistence.entity.User;

import java.time.LocalDateTime;

public class SessionUtil {

    public static Session getSessionWithoutUser(LocalDateTime expiresAt) {
        return new Session(expiresAt);
    }

    public static Session getSessionWithUser(LocalDateTime expiresAt, User user) {
        Session session = new Session(expiresAt);
        session.setUser(user);
        return session;
    }
}
