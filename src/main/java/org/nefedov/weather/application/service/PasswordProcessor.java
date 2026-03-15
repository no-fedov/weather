package org.nefedov.weather.application.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class PasswordProcessor {

    private static final int HASH_ITERATION_DEGREE = 5;

    private final BCrypt.Hasher passwordHasher;
    private final BCrypt.Verifyer passwordVerifyer;

    @Value("${app.security.salt}")
    private String salt;

    public String encode(String password) {
        return passwordHasher.hash(HASH_ITERATION_DEGREE,
                salt.getBytes(StandardCharsets.UTF_8),
                password.getBytes(StandardCharsets.UTF_8))
                .toString();
    }

    public boolean verify(String password, String encodedPassword) {
        return passwordVerifyer.verify(password.getBytes(StandardCharsets.UTF_8),
                HASH_ITERATION_DEGREE, salt.getBytes(StandardCharsets.UTF_8),
                encodedPassword.getBytes(StandardCharsets.UTF_8))
                .verified;
    }
}
