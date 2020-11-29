package com.atm.service.impl;

import com.atm.service.UtilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtilServiceImpl implements UtilService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void runWithTransaction_NEW(Runnable runnable) {
        runnable.run();
    }
}
