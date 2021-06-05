package com.raminq.security.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserModel {
    private Long id;
    private String username;
    private String fullName;
    private String token;
    private String role;
    private Set<String> permissions;
}
