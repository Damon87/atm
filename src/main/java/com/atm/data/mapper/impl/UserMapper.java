package com.atm.data.mapper.impl;

import com.atm.data.domain.security.Authority;
import com.atm.data.domain.security.User;
import com.atm.data.dto.user.CreateUserDTO;
import com.atm.data.dto.user.UpdateUserDTO;
import com.atm.data.dto.user.UserDTO;
import com.atm.data.mapper.CrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements CrudMapper<User, UserDTO, CreateUserDTO, UpdateUserDTO> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User toCreateEntity(CreateUserDTO createUserDTO, List<String> args) {
        User user = new User();

        user.setCardNumber(createUserDTO.getCardNumber());
        user.setPin(bCryptPasswordEncoder.encode(createUserDTO.getPin()));
        user.setAmount(BigDecimal.valueOf(0));
        user.setAuthorities(Collections.singletonList(Authority.ROLE_USER));

        return user;
    }

    @Override
    public User toUpdateEntity(User user, UpdateUserDTO updateUserDTO, List<String> args) {
        user.setAmount(updateUserDTO.getAmount());
        user.setAuthorities(updateUserDTO.getAuthorities().stream().map(Authority::new).collect(Collectors.toList()));

        return user;
    }

    @Override
    public UserDTO toDto(User user, List<String> args) {
        UserDTO userDTO = new UserDTO();

        userDTO.setCardNumber(user.getCardNumber());
        userDTO.setAmount(user.getAmount());
        userDTO.setAuthorities(user.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList()));

        return userDTO;
    }
}
