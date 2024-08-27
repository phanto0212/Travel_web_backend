package com.tourist.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.tourist.Configs.JwtTokenUtil;

import java.io.IOException;

public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenFilter(String defaultFilterProcessesUrl, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        this.jwtTokenUtil = jwtTokenUtil;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String token = jwtTokenUtil.resolveToken(request);
        if (token != null && jwtTokenUtil.validateToken(token)) {
            Authentication authentication = jwtTokenUtil.getAuthentication(token);
            return getAuthenticationManager().authenticate(authentication);
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Authentication authentication = attemptAuthentication(httpRequest, httpResponse);
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}