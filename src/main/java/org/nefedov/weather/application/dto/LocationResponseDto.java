package org.nefedov.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LocationResponseDto {
    private String name;
    private String state;
    private String country;
    @JsonProperty("lat")
    private BigDecimal latitude;
    @JsonProperty("lon")
    private BigDecimal longitude;

    public BigDecimal getLatitude() {
        return latitude != null
                ? latitude.setScale(6, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
                : null;
    }

    public BigDecimal getLongitude() {
        return longitude != null
                ? longitude.setScale(6, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
                : null;
    }
}
