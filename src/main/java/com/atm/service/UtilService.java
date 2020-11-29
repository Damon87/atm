package com.atm.service;

import org.springframework.transaction.annotation.Transactional;

public interface UtilService {
    @Transactional
    void runWithTransaction_NEW(Runnable runnable);

}
