package com.raminq.security.web.api.security;


import com.raminq.security.domain.dto.*;
import com.raminq.security.service.security.AuthService;
import com.raminq.security.service.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("login")
    public UserModel login(@RequestBody @Valid LoginModel model) {
        return authService.login(model);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest model) {
        return refreshTokenService.generateNewRefreshToken(model.getRefreshToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@Valid @RequestBody LogOutRequest request) {
        refreshTokenService.deleteByUserId(request.getUserId());
        return ResponseEntity.ok("Log out successful!");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> generateForgotPasswordToken(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.generateForgotPasswordToken(request));
    }

    @PostMapping("/confirm-forgot-password")
    public ResponseEntity<String> confirmForgotPasswordToken(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.confirmForgotPasswordToken(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }
}
