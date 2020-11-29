package com.atm.controller;

import com.atm.data.dto.user.CreateUserDTO;
import com.atm.data.objects.DataWrapper;
import com.atm.service.SecurityService;
import com.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public DataWrapper<String> registration(@RequestBody CreateUserDTO CreateUserDTO) {
        return DataWrapper.of(userService.registration(CreateUserDTO));
    }

    @PostMapping("/login")
    public DataWrapper<String> login(String cardNumber, String pin) {
        return DataWrapper.of(securityService.login(cardNumber, pin));
    }

}
