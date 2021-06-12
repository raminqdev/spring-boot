package com.raminq.security.service.security;

import com.raminq.security.configuration.security.JwtTokenUtil;
import com.raminq.security.domain.dto.LoginModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;

    public ResponseEntity<UserModel> login(LoginModel loginModel) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

        User user = (User) authenticate.getPrincipal();
        UserModel userModel = userMapper.toUserModel(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userModel))
                .body(userModel);
    }
}
