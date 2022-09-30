package com.cspro.authentication.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;

@Getter
public class AppUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String jwt;
    public AppUserAuthenticationToken(Object principal, Object credential, String jwt) {
        super(principal, credential, new ArrayList<>());
        this.jwt = jwt;
    }
}
