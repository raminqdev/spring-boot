package com.raminq.security.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordRequest {

    @NotBlank
    public String username;
    public String newPassword;
    public String forgotPasswordToken;

}
