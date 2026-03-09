package org.nefedov.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationUserResponseDto {
    private String name;
    private String state;
    private String country;
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lon")
    private Double longitude;
    private Boolean isAdded;
}
