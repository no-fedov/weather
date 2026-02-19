package integration.persistence;

import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.nefedov.weather.application.persistence.repository.LocationRepositoryImpl;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import util.LocationUtil;

import java.math.BigDecimal;

import static util.LocationUtil.getLocationWithoutId;
import static util.UserUtil.getUserWithoutId;

public class LocationRepositoryImplTest extends RepositoryTest {

    private static final String LOCATION_NAME = "MOSCOW";

    private static final Double LOCATION_LATITUDE = 10d;
    private static final Double LOCATION_LONGITUDE = 20d;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void saveLocationForUser_Successful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        Location location = getLocationWithoutId(LOCATION_NAME, LOCATION_LATITUDE, LOCATION_LONGITUDE);
        user.addLocation(location);
        userRepository.save(user);

        Integer locationId = location.getId();

        locationRepository.findById(locationId).orElseThrow();
    }

    @Test
    public void findLocationByCoordinate_Successful() {
        Location location = getLocationWithoutId(LOCATION_NAME, LOCATION_LATITUDE, LOCATION_LONGITUDE);
        locationRepository.save(location);

        locationRepository.findByCoordinate(LOCATION_LATITUDE, LOCATION_LONGITUDE).orElseThrow();
    }
}
