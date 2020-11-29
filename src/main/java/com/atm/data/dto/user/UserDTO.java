package com.atm.data.dto.user;

import com.atm.data.dto.card_transaction.TransactionDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserDTO {
    private String cardNumber;
    private BigDecimal amount;
    private List<String> authorities;
    private List<TransactionDTO> transactions;
}
