package com.raminq.security.web.api.security;


import com.raminq.security.domain.dto.LoginModel;
import com.raminq.security.domain.dto.RegisterModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.service.security.AuthService;
import com.raminq.security.service.security.UserService;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;

    @PostMapping("login")
    public UserModel login(@RequestBody @Valid LoginModel model) {
        return authService.login(model);
    }

//    @PostMapping("register")
//    public UserModel register(@RequestBody @Valid RegisterModel model) {
//        return userService.create(model);
//    }

}
