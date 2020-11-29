package com.atm.controller;

import com.atm.data.domain.security.User;
import com.atm.data.dto.card_transaction.CreateTransactionDTO;
import com.atm.data.dto.card_transaction.SearchTransactionDTO;
import com.atm.data.dto.card_transaction.TransactionDTO;
import com.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public TransactionDTO create(@AuthenticationPrincipal User user, @RequestBody CreateTransactionDTO createTransactionDTO) {
        return transactionService.create(user, createTransactionDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public TransactionDTO getById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return transactionService.getById(user, id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/all-by-user")
    public List<TransactionDTO> search(@AuthenticationPrincipal User user, Pageable pageable) {
        return transactionService.getUserTransactions(user, pageable);
    }
}
