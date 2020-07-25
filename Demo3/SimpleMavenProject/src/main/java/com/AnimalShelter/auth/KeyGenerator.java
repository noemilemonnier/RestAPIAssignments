package com.AnimalShelter.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyGenerator {
    private static KeyGenerator ourInstance = new KeyGenerator();

    public static KeyGenerator getInstance() {
        return ourInstance;
    }

    private Key key;

    private KeyGenerator() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public Key getKey() {
        return key;
    }
}
