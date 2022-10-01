package com.cspro.authentication.exception;


import org.springframework.security.core.AuthenticationException;

public class UserAccessDeniedException extends AuthenticationException {
    public UserAccessDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
