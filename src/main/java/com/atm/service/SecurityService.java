package com.atm.service;

import com.atm.data.domain.security.User;

public interface SecurityService {
    String login(String cardNumber, String pin);

    Boolean changePin(User user, String oldPin, String newPin);
}
