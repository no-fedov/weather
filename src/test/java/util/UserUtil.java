package util;

import org.nefedov.weather.application.persistence.entity.User;

public class UserUtil {

    public static User getUserWithoutId(String login, String password) {
        return new User(null, login, password, null);
    }

    public static User getUserWithId(Integer id, String login, String password) {
        return new User(id, login, password, null);
    }
}
