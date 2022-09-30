package com.cspro.authentication.controller;

import com.cspro.authentication.dto.AppUserDto;
import com.cspro.authentication.security.AppUserAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public ResponseEntity<String> homePageHandler() {
        return new ResponseEntity<>("This is home and you have access for this anyways!!!", HttpStatus.OK);
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedPageHandler() {
        return new ResponseEntity<>("This is protected and you only have access if you are authorized. Congo you are authorized!!!", HttpStatus.OK);
    }


    @PostMapping("/api/login")
    public ResponseEntity<AppUserAuthenticationToken> loginUser(@RequestBody AppUserDto appUserDto) {
        log.info("Data coming here {}", appUserDto);
        AppUserAuthenticationToken appUserAuthenticationToken = new AppUserAuthenticationToken(appUserDto.getUsername(), appUserDto.getPassword(), null);
        appUserAuthenticationToken = (AppUserAuthenticationToken) authenticationManager.authenticate(appUserAuthenticationToken);
        return new ResponseEntity<>(appUserAuthenticationToken, HttpStatus.OK);
    }

}
