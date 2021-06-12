package com.raminq.security.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserFullModel {
    private Long id;
    private String username;
    private String fullName;
    private boolean enabled;
    private String role;
    private Set<String> permissions;
}
