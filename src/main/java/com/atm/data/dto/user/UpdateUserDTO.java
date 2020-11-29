package com.atm.data.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateUserDTO {

    @NotNull
    @NotEmpty
    private BigDecimal amount;

    @NotNull
    @NotEmpty
    private List<String> authorities;
}
