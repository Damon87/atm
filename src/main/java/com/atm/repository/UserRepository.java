package com.atm.repository;

import com.atm.data.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByCardNumber(String cardNumber);
}
