package com.redtailsoft.springbootauthupdated.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.redtailsoft.springbootauthupdated.security.SecurityConstants.EXPIRATION_TIME;
import static com.redtailsoft.springbootauthupdated.security.SecurityConstants.HEADER_STRING;
import static com.redtailsoft.springbootauthupdated.security.SecurityConstants.SECRET;
import static com.redtailsoft.springbootauthupdated.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redtailsoft.springbootauthupdated.user.ApplicationUser;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWTAuthenticationFilter
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            ApplicationUser creds = new ObjectMapper()
                .readValue(request.getInputStream(), ApplicationUser.class);

            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(), 
                    new ArrayList<>()));

            return auth;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
                String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

    
}