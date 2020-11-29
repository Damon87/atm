package com.atm.service.impl;

import com.atm.configuration.secutiry.token.AuthenticationTokenProvider;
import com.atm.data.domain.security.User;
import com.atm.repository.UserRepository;
import com.atm.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    private AccountStatusUserDetailsChecker accountStatusUserDetailsChecker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String login(String cardNumber, String pin) {
        User user = userRepository.getOne(cardNumber);

        if (user == null) {
            throw new IllegalStateException("User not found");
        }

        if (!passwordEncoder.matches(pin, user.getPin())) {
            throw new IllegalStateException("Forbidden");
        }

        accountStatusUserDetailsChecker.check(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, pin, user.getAuthorities());
        return authenticationTokenProvider.createToken(authenticationToken);
    }

    @Override
    public Boolean changePin(User user, String oldPin, String newPin) {
        if (user == null) {
            return false;
        }

        if (!bCryptPasswordEncoder.matches(oldPin, user.getPassword())) {
            throw new IllegalStateException("Wrong credentials");
        }

        user.setPin(bCryptPasswordEncoder.encode(newPin));
        userRepository.save(user);

        return true;
    }

}
