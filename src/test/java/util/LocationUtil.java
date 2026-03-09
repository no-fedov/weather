package util;

import org.nefedov.weather.application.persistence.entity.Location;

import java.math.BigDecimal;

public class LocationUtil {

    public static Location getLocationWithoutId(String name, BigDecimal latitude, BigDecimal longitude) {
        return new Location(name, latitude, longitude);
    }

    public static Location getUserWithId(Integer id, String name, BigDecimal latitude, BigDecimal longitude) {
        Location location = new Location(name, latitude, longitude);
        location.setId(id);
        return location;
    }
}
