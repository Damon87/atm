package com.atm.service;

import com.atm.data.domain.security.User;
import com.atm.data.dto.card_transaction.CreateTransactionDTO;
import com.atm.data.dto.card_transaction.TransactionDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    TransactionDTO create(User user, CreateTransactionDTO createTransactionDTO);

    TransactionDTO getById(User user, Long id);

    List<TransactionDTO> getUserTransactions(User user, Pageable pageable);
}
