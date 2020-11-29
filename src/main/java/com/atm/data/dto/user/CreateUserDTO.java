package com.atm.data.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserDTO {

    @NotNull
    @NotEmpty
    private String cardNumber;

    @NotNull
    @NotEmpty
    private String pin;

}
