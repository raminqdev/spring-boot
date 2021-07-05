package com.raminq.security.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateModel {
    @NotBlank
    private String fullName;
    private boolean enabled;
    @NotBlank
    private String role;
}
