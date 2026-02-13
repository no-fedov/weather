package org.nefedov.weather.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    private String login;
    private String password;
    private String repeatedPassword;

    public UserCreateDto(String login, String password, String repeatedPassword) {
        this.login = login;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }
}
