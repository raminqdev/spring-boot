package com.raminq.security.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginModel {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
