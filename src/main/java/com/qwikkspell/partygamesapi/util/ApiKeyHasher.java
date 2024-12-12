package com.qwikkspell.partygamesapi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ApiKeyHasher {

    public static String hashApiKey(String apiKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(apiKey.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("error hashing API key", e);
        }
    }
}
