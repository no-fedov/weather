package org.nefedov.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherExternalResponseDto {

    @JsonProperty("coord")
    private Coordinate coordinate;
    @JsonProperty("weather")
    private List<Weather> weather;
    @JsonProperty("main")
    private Main main;
    @JsonProperty("name")
    private String cityName;
    @JsonProperty("sys")
    public Sys sys;

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
        private String icon;
    }

    @Getter
    @Setter
    public static class Main {
        @JsonProperty("temp")
        private String temperature;
        @JsonProperty("feels_like")
        private String feelsLike;
        private String humidity;
    }

    public static class Sys {
        @JsonProperty("country")
        public String country;
    }
}
