package org.nefedov.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherClientResponseDto {

    @JsonProperty("coord")
    private Coordinate coordinate;
    @JsonProperty("weather")
    private List<Weather> weather;
    @JsonProperty("main")
    private Temperature temperature;

    @Getter
    @Setter
    public static class Coordinate {
        private String lon;
        private String lat;
    }

    @Getter
    @Setter
    public static class Weather {
        private String main;
        private String description;
    }

    @Getter
    @Setter
    public static class Temperature {
        @JsonProperty("temp")
        private String temperature;
        @JsonProperty("feels_like")
        private String feelsLike;
    }
}
