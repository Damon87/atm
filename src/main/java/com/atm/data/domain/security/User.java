package com.atm.data.domain.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "pin")
    private String pin;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", nullable = false)}
    )
    private Collection<Authority> authorities;

    @Column
    private boolean accountNonExpired = true;

    @Column
    private boolean accountNonLocked = true;

    @Column
    private boolean credentialsNonExpired = true;

    @Column
    private boolean enabled = true;

    @Override
    public String getPassword() {
        return pin;
    }

    @Override
    public String getUsername() {
        return cardNumber;
    }

}

