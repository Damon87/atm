package com.atm.data.mapper.impl;

import com.atm.data.domain.Transaction;
import com.atm.data.dto.card_transaction.CreateTransactionDTO;
import com.atm.data.dto.card_transaction.TransactionDTO;
import com.atm.data.dto.card_transaction.UpdateTransactionDTO;
import com.atm.data.mapper.CrudMapper;
import com.atm.data.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class TransactionMapper implements CrudMapper<Transaction, TransactionDTO, CreateTransactionDTO, UpdateTransactionDTO> {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Transaction toCreateEntity(CreateTransactionDTO createTransactionDTO, List<String> args) {
        Transaction transaction = new Transaction();

        transaction.setIsAddAmount(createTransactionDTO.getIsAddAmount());
        transaction.setAmount(createTransactionDTO.getAmount());
        transaction.setDate(Instant.now());

        return transaction;
    }

    @Override
    public Transaction toUpdateEntity(Transaction transaction, UpdateTransactionDTO updateTransactionDTO, List<String> args) {
        return transaction;
    }

    @Override
    public TransactionDTO toDto(Transaction transaction, List<String> args) {
        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setId(transaction.getId());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setIsAddAmount(transaction.getIsAddAmount());
        transactionDTO.setAmount(transaction.getAmount());

        if (args.contains(Mapper.SHOW_PARENT)) {
            transactionDTO.setUserDTO(userMapper.toDto(transaction.getUser()));
        }

        return transactionDTO;
    }
}
