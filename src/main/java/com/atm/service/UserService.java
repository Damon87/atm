package com.atm.service;

import com.atm.data.domain.security.User;
import com.atm.data.dto.user.CreateUserDTO;

import java.math.BigDecimal;

public interface UserService {
    String registration(CreateUserDTO createUserDTO);

    User getByCardNumber(String cardNumber);

    BigDecimal getOwnAmount(User user);
}
