package com.atm.configuration.secutiry;

import com.atm.data.domain.security.Authority;
import com.atm.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class DbInitializer {

    @Autowired
    private AuthorityRepository authorityRepository;

    @PostConstruct
    private void initAuthority() {
        Arrays.stream(Authority.AuthorityType.values()).forEach(authorityType -> {
            Authority authority = authorityRepository.findById(authorityType).orElse(null);

            if (authority == null) {
                authorityRepository.save(new Authority(authorityType));
            }
        });
    }

}
