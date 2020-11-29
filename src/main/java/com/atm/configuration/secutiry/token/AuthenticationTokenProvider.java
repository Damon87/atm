package com.atm.configuration.secutiry.token;

import com.atm.data.domain.security.User;
import com.atm.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:security.properties")
public class AuthenticationTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String CARD_NUMBER = "card";

    @Autowired
    private AccountStatusUserDetailsChecker accountStatusUserDetailsChecker;

    @Autowired
    private UserService userService;

    public String createToken(Authentication authentication) {
        return createToken(UserTokenInfo.of((User) authentication.getPrincipal()));
    }

    public String createToken(UserTokenInfo userTokenInfo) {
        if (userTokenInfo == null) {
            return null;
        }

        String authorities = userTokenInfo.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return TokenManager.AUTH.sign(
                userTokenInfo.getCardNumber(),
                jwtBuilder -> jwtBuilder
                        .claim(AUTHORITIES_KEY, authorities)
                        .claim(CARD_NUMBER, userTokenInfo.getCardNumber())
        );
    }

    public Authentication getAuthentication(String token) {
        Claims claims = TokenManager.AUTH.parseClaims(token);

        User principal = resolveUser(claims);

        if (principal == null) {
            throw new IllegalStateException("No such user");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        accountStatusUserDetailsChecker.check(principal);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public User resolveUser(Claims claims) {
        return userService.getByCardNumber(claims.get(CARD_NUMBER, String.class));
    }

    public boolean validateAuthToken(String authToken) {
        return TokenManager.AUTH.validate(authToken);
    }

}
