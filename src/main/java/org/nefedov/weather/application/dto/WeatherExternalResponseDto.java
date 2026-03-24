package org.nefedov.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WeatherExternalResponseDto {

    @JsonProperty("coord")
    private Coordinate coordinate;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("main")
    private Main main;

    @EqualsAndHashCode.Include
    @JsonProperty("name")
    private String cityName;

    @JsonProperty("sys")
    public Sys sys;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coordinate {

        @EqualsAndHashCode.Include
        private String lon;

        @EqualsAndHashCode.Include
        private String lat;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {

        private String main;

        private String description;

        private String icon;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {

        @JsonProperty("temp")
        private String temperature;

        @JsonProperty("feels_like")
        private String feelsLike;

        private String humidity;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sys {

        @JsonProperty("country")
        public String country;
    }
}
