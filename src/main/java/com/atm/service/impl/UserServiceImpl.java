package com.atm.service.impl;

import com.atm.configuration.secutiry.token.AuthenticationTokenProvider;
import com.atm.configuration.secutiry.token.UserTokenInfo;
import com.atm.data.domain.security.User;
import com.atm.data.dto.user.CreateUserDTO;
import com.atm.data.mapper.impl.UserMapper;
import com.atm.repository.UserRepository;
import com.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Override
    public String registration(CreateUserDTO createUserDTO) {
        User user = userRepository.save(userMapper.toCreateEntity(createUserDTO));
        return authenticationTokenProvider.createToken(UserTokenInfo.of(user));
    }

    @Override
    public User getByCardNumber(String cardNumber) {
        if (StringUtils.isEmpty(cardNumber)) {
            return null;
        }

        return userRepository.findByCardNumber(cardNumber);
    }

    @Override
    public BigDecimal getOwnAmount(User user) {
        return user.getAmount();
    }
}
