package integration.persistence;

import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.dto.LocationDto;
import org.nefedov.weather.application.dto.LocationResponseDto;
import org.nefedov.weather.application.persistence.entity.Location;
import org.nefedov.weather.application.persistence.entity.User;
import org.nefedov.weather.application.persistence.mapper.LocationMapper;
import org.nefedov.weather.application.persistence.repository.LocationRepository;
import org.nefedov.weather.application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.LocationUtil.getLocationWithoutId;
import static util.UserUtil.getUserWithoutId;

public class LocationRepositoryImplTest extends RepositoryTest {

    private static final String LOCATION_NAME = "MOSCOW";
    private static final BigDecimal LOCATION_LATITUDE = new BigDecimal(10);
    private static final BigDecimal LOCATION_LONGITUDE = new BigDecimal(20);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

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
    public void deleteLocationForUser_successful() {
        User user = getUserWithoutId(USER_LOGIN_TEST, USER_PASSWORD_TEST);
        Location location = getLocationWithoutId(LOCATION_NAME, LOCATION_LATITUDE, LOCATION_LONGITUDE);
        user.addLocation(location);
        userRepository.save(user);
        Integer userId = user.getId();
        LocationDto locationResponseDto = locationMapper.toCoordinate(location);
        locationRepository.deleteLocationForUser(locationResponseDto, userId);

        assertTrue(user.getLocations().isEmpty());
    }
}
