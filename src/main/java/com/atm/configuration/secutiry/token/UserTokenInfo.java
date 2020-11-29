package com.atm.configuration.secutiry.token;

import com.atm.data.domain.security.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserTokenInfo {

    private String cardNumber;

    private Collection<? extends GrantedAuthority> authorities;

    public UserTokenInfo(String cardNumber, Collection<? extends GrantedAuthority> authorities) {
        this.cardNumber = cardNumber;
        this.authorities = authorities;
    }

    public static UserTokenInfo of(User user) {
        if (user == null) {
            return null;
        }

        return new UserTokenInfo(
                user.getCardNumber(),
                user.getAuthorities()
        );
    }

}

