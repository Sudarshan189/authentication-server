package com.cspro.authentication.security.provider;

import com.cspro.authentication.exception.UserAccessDeniedException;
import com.cspro.authentication.exception.UserNotFoundException;
import com.cspro.authentication.model.AppUser;
import com.cspro.authentication.security.AppUserAuthenticationToken;
import com.cspro.authentication.service.AppUserService;
import com.cspro.authentication.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
@Slf4j
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AppUserService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            AppUser userDetails = (AppUser) userDetailsService.loadUserByUsername(username);
            if (BCrypt.checkpw(password, userDetails.getPassword())) {
                return new AppUserAuthenticationToken(userDetails.getUsername(), null, jwtHelper.createJwt(userDetails));
            }
            throw new UserAccessDeniedException("Username or password mismatch", null);

        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("Username not found", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
