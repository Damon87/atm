package com.atm.configuration.secutiry;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.security.SecureRandom;

import static org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.DEFAULT_PROTOCOL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@PropertySource("classpath:security.properties")
public class SecurityConfiguration {

    @Value("${security.passkey.secret}")
    private String securityKey;

    @Value("${security.passkey.strength}")
    private Integer strength;

    @Value("${server.default-port}")
    private Integer defaultPort;

    @Value("${server.port}")
    private Integer port;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength, new SecureRandom(Hex.decode(securityKey)));
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    @Bean
    public AccountStatusUserDetailsChecker accountStatusUserDetailsChecker() {
        return new AccountStatusUserDetailsChecker();
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(defaultPort);
        connector.setSecure(false);
        connector.setRedirectPort(port);
        return connector;
    }

}