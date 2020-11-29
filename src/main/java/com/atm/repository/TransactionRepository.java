package com.atm.repository;

import com.atm.data.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.user.cardNumber = :#{#userCardNumber}")
    List<Transaction> search(String userCardNumber, Pageable pageable);
}
