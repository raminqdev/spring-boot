package com.raminq.security.service.security;

import com.raminq.security.configuration.security.JwtTokenUtil;
import com.raminq.security.domain.dto.LoginModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public UserModel login(LoginModel loginModel) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

        User user = (User) authenticate.getPrincipal();

        UserModel userModel = userMapper.toUserModel(user);
        userModel.setToken(jwtTokenUtil.generateAccessToken(user));
        userModel.setRefreshToken(refreshTokenService.createRefreshToken(user));

        return userModel;
    }
}
