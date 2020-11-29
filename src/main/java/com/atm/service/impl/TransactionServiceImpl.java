package com.atm.service.impl;

import com.atm.configuration.Constants;
import com.atm.data.domain.Transaction;
import com.atm.data.domain.security.User;
import com.atm.data.dto.card_transaction.CreateTransactionDTO;
import com.atm.data.dto.card_transaction.TransactionDTO;
import com.atm.data.mapper.impl.TransactionMapper;
import com.atm.repository.AtmRepository;
import com.atm.repository.TransactionRepository;
import com.atm.repository.UserRepository;
import com.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AtmRepository atmRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TransactionDTO create(User user, CreateTransactionDTO createTransactionDTO) {
        Transaction transaction = transactionMapper.toCreateEntity(createTransactionDTO);
        Long atmAmount = atmRepository.getOne(createTransactionDTO.getAtmId()).getAmount();
        Boolean isAddAmount = transaction.getIsAddAmount();
        BigDecimal transactionAmount = transaction.getAmount();

        if (!isAddAmount) {
            if (transactionAmount.doubleValue() % 50 > 0) {
                throw new IllegalStateException("You wrote not correct amount. Try again");
            }

            if (BigDecimal.valueOf(atmAmount).compareTo(transactionAmount) < 0) {
                throw new IllegalStateException("Atm does not have enough money. You can visit such nearest atm's: " + Constants.NEAREST_ATM_ADDRESS);
            }
        }

        user.setAmount(isAddAmount ? user.getAmount().add(transactionAmount) : user.getAmount().subtract(transactionAmount));

        if (user.getAmount().compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalStateException("No such money for this transaction");
        }

        transaction.setUser(user);
        userRepository.save(user);
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDTO getById(User user, Long id) {
        Transaction transaction = transactionRepository.getOne(id);

        if (!transaction.getUser().getCardNumber().equals(user.getCardNumber())) {
            throw new IllegalStateException("This transaction not yours");
        }

        return transactionMapper.toDto(transaction);
    }

    @Override
    public List<TransactionDTO> getUserTransactions(User user, Pageable pageable) {
        return transactionMapper.toDto(transactionRepository.search(user.getCardNumber(), pageable));
    }
}
