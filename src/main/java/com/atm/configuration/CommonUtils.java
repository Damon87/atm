package com.atm.configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class CommonUtils {

    public static final class TOKEN {
        public static Key makeKey(String secret) {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }
}
