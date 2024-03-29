package com.ict.careus.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String phoneNumber;

    private Set<String> role;

    private String password;
}