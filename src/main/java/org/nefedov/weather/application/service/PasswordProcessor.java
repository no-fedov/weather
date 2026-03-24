package org.nefedov.weather.application.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordProcessor {

    private static final int HASH_ITERATION_DEGREE = 5;

    private final BCrypt.Hasher passwordHasher;
    private final BCrypt.Verifyer passwordVerifyer;

    public String encode(String password) {
        return passwordHasher.hashToString(HASH_ITERATION_DEGREE, password.toCharArray());
    }

    public boolean verify(String password, String encodedPassword) {
        return passwordVerifyer.verify(password.toCharArray(), encodedPassword.toCharArray()).verified;
    }
}
