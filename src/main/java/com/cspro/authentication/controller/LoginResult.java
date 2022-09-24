package com.cspro.authentication.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor
public class LoginResult {
   
   @NonNull
   private String jwt;
}