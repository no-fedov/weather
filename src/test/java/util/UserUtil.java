package util;

import org.nefedov.weather.application.persistence.entity.User;

public class UserUtil {

    public static User getUserWithoutId(String login, String password) {
        return new User(login, password);
    }

    public static User getUserWithId(Integer id, String login, String password) {
        User user = new User(login, password);
        user.setId(id);
        return user;
    }
}
