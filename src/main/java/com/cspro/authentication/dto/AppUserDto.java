package com.cspro.authentication.dto;

import lombok.Data;

@Data
public class AppUserDto {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "AppUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
