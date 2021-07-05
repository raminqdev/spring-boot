package com.raminq.security.service.security;

import com.raminq.security.configuration.security.JwtTokenUtil;
import com.raminq.security.domain.dto.ForgotPasswordRequest;
import com.raminq.security.domain.dto.LoginModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.exception.NotFoundException;
import com.raminq.security.domain.mapper.UserMapper;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.raminq.security.domain.dto.ErrorCodes.USER_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public UserModel login(LoginModel loginModel) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

        User user = (User) authenticate.getPrincipal();

        UserModel userModel = userMapper.toUserModel(user);
        userModel.setAccessToken(jwtTokenUtil.generateAccessToken(user));
        userModel.setRefreshToken(refreshTokenService.generateRefreshToken(user));

        return userModel;
    }

    @Transactional
    public String generateForgotPasswordToken(ForgotPasswordRequest request) {
        User user = userRepo.findByUsername(request.username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_BY_ID));

        String forgotPasswordToken = String.format("%06d", new Random().nextInt(999999));
        user.setForgotPasswordToken(forgotPasswordToken);
        userRepo.save(user);
        System.out.println(forgotPasswordToken);
        return "Check you're email.";
    }

    public String confirmForgotPasswordToken(ForgotPasswordRequest request) {
        User user = userRepo.findByUsername(request.username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_BY_ID));

        if (user.getForgotPasswordToken().equals(request.getForgotPasswordToken()))
            return "Success";
        return "Failed. forgot password don't match!";
    }

    @Transactional
    public String resetPassword(ForgotPasswordRequest request) {
        User user = userRepo.findByUsername(request.username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_BY_ID));

        if (user.getForgotPasswordToken().equals(request.getForgotPasswordToken())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepo.save(user);
        }
        return "Success";
    }
}
