package com.atm.configuration.secutiry.filter;

import com.atm.configuration.secutiry.token.AuthenticationTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthenticationTokenProvider authenticationTokenProvider;

    public JWTFilter(AuthenticationTokenProvider authenticationTokenProvider) {
        this.authenticationTokenProvider = authenticationTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String jwt = resolveToken(httpServletRequest);

        if (StringUtils.hasText(jwt) && authenticationTokenProvider.validateAuthToken(jwt)) {
            try {
                Authentication authentication = authenticationTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                httpServletResponse.getWriter().write(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
