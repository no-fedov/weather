package util;

import org.nefedov.weather.application.persistence.entity.Location;

public class LocationUtil {

    public static Location getLocationWithoutId(String name, Double latitude, Double longitude) {
        return new Location(name, latitude, longitude);
    }

    public static Location getUserWithId(Integer id, String name, Double latitude, Double longitude) {
        Location location = new Location(name, latitude, longitude);
        location.setId(id);
        return location;
    }
}
