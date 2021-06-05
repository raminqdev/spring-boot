package com.raminq.security.service.security;

import com.raminq.security.configuration.security.JwtTokenUtil;
import com.raminq.security.domain.dto.LoginModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.entity.security.Permission;
import com.raminq.security.domain.entity.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public UserModel login(LoginModel loginModel) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

        User user = (User) authenticate.getPrincipal();

        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setUsername(user.getUsername());
        userModel.setFullName(user.getFullName());
        userModel.setToken(jwtTokenUtil.generateAccessToken(user));
        userModel.setRole(user.getRole().getName());
        userModel.setPermissions(user.getRole().getPermissions().stream().map(Permission::getName)
                .collect(Collectors.toSet()));

        return userModel;
    }
}
