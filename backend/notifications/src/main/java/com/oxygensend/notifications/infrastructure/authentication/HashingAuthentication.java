package com.oxygensend.notifications.infrastructure.authentication;

import com.oxygensend.notifications.context.authentication.AuthException;
import com.oxygensend.notifications.context.authentication.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;

@Component
public class HashingAuthentication implements Authentication {

    private static final Charset ENCODING = StandardCharsets.UTF_8;

    @Override
    public void authenticate(Map<String, String> params) throws AuthException {
        var plainPassword = params.get("password");
        if (plainPassword == null) {
            throw new IllegalArgumentException("Password is required");
        }
        var hashedPassword = params.get("hashedPassword");
        if (hashedPassword == null) {
            throw new IllegalArgumentException("Hashed password is required");
        }

        byte[] hash = calculateHash(plainPassword);
        byte[] hashPassword = HexFormat.of().parseHex(hashedPassword);
        if (!MessageDigest.isEqual(hash, hashPassword)) {
            throw new AuthException("Invalid password");
        }
    }

    private byte[] calculateHash(String plainPassword) throws AuthException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = plainPassword.getBytes(ENCODING);
            byte[] first = md.digest(passwordBytes);
            return md.digest(first);
        } catch (NoSuchAlgorithmException e) {
            throw new AuthException(e.getMessage());
        }
    }
}
