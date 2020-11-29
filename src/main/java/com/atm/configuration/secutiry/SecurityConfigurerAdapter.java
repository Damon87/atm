package com.atm.configuration.secutiry;

import com.atm.configuration.secutiry.filter.JWTFilter;
import com.atm.configuration.secutiry.token.AuthenticationTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                .csrf().disable();

        http.addFilterBefore(new JWTFilter(authenticationTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

}