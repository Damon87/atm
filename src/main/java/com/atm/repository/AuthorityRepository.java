package com.atm.repository;

import com.atm.data.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Authority.AuthorityType> {
}
