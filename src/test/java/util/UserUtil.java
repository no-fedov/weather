package util;

import org.nefedov.weather.persistence.entity.User;

public class UserUtil {

    public static User getUserWithoutId(String login, String password) {
        return new User(null, login, password, null);
    }
}
