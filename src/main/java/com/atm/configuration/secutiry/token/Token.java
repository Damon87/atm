package com.atm.configuration.secutiry.token;

import com.atm.configuration.CommonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;
import java.util.function.Consumer;

@Getter
public class Token {

    private static final Logger log = LoggerFactory.getLogger(Token.class);

    private String secret;
    private Long validity;

    private Key key;

    Token() {
    }

    void init(String secret, Long validity) {
        this.secret = secret;
        this.validity = validity;

        this.key = CommonUtils.TOKEN.makeKey(secret);
    }

    public boolean validate(String tokenValue) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(tokenValue);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature");
            log.trace("Invalid JWT signature", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
            log.trace("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
            log.trace("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid");
            log.trace("JWT token compact of handler are invalid", e);
        }

        return false;
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String sign(String subject, Consumer<JwtBuilder> jwtBuilderConsumer) {
        long now = new Date().getTime();
        Date validityDate = new Date(now + validity);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(subject)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validityDate);

        if (jwtBuilderConsumer != null) {
            jwtBuilderConsumer.accept(jwtBuilder);
        }

        return jwtBuilder.compact();
    }

}
