package com.cspro.authentication.service;

import com.cspro.authentication.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return AppUser.builder()
                .id("sudarshan")
                .name("Sudarshan Shanbhag")
                .active(true)
                .admin(false)
                .version(1L)
                .password(passwordEncoder.encode("test"))
                .country("India")
                .build();
    }
}
