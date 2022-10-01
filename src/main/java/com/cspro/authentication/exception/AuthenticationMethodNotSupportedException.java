package com.cspro.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationMethodNotSupportedException extends AuthenticationException {
    public AuthenticationMethodNotSupportedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
