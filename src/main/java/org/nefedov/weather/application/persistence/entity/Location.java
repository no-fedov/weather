package org.nefedov.weather.application.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nefedov.weather.application.persistence.entity.converter.CoordinateConverter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "location",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_lat_lon", columnNames = {"latitude", "longitude"})
        })
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "latitude", nullable = false)
    @Convert(converter = CoordinateConverter.class)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    @Convert(converter = CoordinateConverter.class)
    private BigDecimal longitude;

    public Location(String name, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}