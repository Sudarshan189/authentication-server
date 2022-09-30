package com.cspro.authentication.security.manager;

import com.cspro.authentication.exception.AuthenticationMethodNotSupportedException;
import com.cspro.authentication.security.provider.UserNamePasswordAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserNamePasswordAuthenticationProvider userNamePasswordAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (userNamePasswordAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class)) {
            return userNamePasswordAuthenticationProvider.authenticate(authentication);
        }
        throw new AuthenticationMethodNotSupportedException("Authentication method not supported", null);
    }
}
