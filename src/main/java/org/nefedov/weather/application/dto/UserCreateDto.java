package org.nefedov.weather.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    private String login;
    private String password;
    private String repeatedPassword;
}
