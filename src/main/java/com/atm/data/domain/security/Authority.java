package com.atm.data.domain.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    public static final Authority ROLE_USER = new Authority(AuthorityType.ROLE_USER);
    public static final Authority ROLE_ADMIN = new Authority(AuthorityType.ROLE_ADMIN);
    public static final Authority ROLE_OWNER = new Authority(AuthorityType.ROLE_OWNER);

    public enum AuthorityType {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_OWNER
    }

    @Id
    @Column(name = "name", nullable = false, length = 36)
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    public Authority(AuthorityType authority) {
        this.authority = authority;
    }

    public Authority(String authority) {
        this.authority = AuthorityType.valueOf(authority);
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority1 = (Authority) o;

        return authority == authority1.authority;
    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }

}

