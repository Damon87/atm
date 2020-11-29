package com.atm.configuration.secutiry.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TokenManager {

    @Value("${security.token.auth.secret}")
    private String authTokenSecret;

    @Value("${security.token.auth.validity}")
    private Long authTokenValidity;

    public static final Token AUTH = new Token();

    @PostConstruct
    private void init() {
        AUTH.init(authTokenSecret, authTokenValidity);
    }

}
