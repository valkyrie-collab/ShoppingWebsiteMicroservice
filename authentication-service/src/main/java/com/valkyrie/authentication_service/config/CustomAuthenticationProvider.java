package com.valkyrie.authentication_service.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder ENCODER;
    private final CustomUserDetailsService service;

    public CustomAuthenticationProvider(BCryptPasswordEncoder ENCODER, CustomUserDetailsService service) {
        this.ENCODER = ENCODER; this.service = service;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getAuthorities().toString();
        UserDetails userDetails = service.loadUserByUsername(username);

        if (!ENCODER.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Password EROR");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
