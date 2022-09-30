package com.cspro.authentication.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
