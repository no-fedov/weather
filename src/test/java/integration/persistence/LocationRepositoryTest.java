package integration.persistence;

import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static util.UserUtil.getUserWithoutId;

public class LocationRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void saveLocationForUser_Successful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        Location location = new Location("MOSCOW", new BigDecimal(10), new BigDecimal(20));
        user.addLocation(location);
        userRepository.save(user);

        Integer locationId = location.getId();

        locationRepository.findById(locationId).orElseThrow();
    }
}
