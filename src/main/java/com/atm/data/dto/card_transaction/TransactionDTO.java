package com.atm.data.dto.card_transaction;

import com.atm.data.dto.user.UserDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionDTO {
    private Long id;
    private Instant date;
    private Boolean isAddAmount;
    private BigDecimal amount;
    private UserDTO userDTO;
}
