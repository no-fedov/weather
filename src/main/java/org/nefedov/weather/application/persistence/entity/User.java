package org.nefedov.weather.application.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_location",
            joinColumns = @JoinColumn(table = "users", name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "user_location_user_fk"),
            inverseJoinColumns = @JoinColumn(table = "location", name = "location_id", referencedColumnName = "id"),
            inverseForeignKey = @ForeignKey(name = "user_location_location_fk")
    )

    private Set<Location> locations = new HashSet<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }
}
