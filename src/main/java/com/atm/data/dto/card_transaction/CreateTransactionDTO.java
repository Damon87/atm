package com.atm.data.dto.card_transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionDTO {
    private Boolean isAddAmount;
    private BigDecimal amount;
    private Long atmId;
}
