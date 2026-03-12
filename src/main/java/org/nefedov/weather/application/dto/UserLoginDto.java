package org.nefedov.weather.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginDto {

    @NotBlank(message = "Login is required")
    @Size(min = 5, max = 30, message = "Login must be between 6 and 30 characters")
    private String login;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;
}
