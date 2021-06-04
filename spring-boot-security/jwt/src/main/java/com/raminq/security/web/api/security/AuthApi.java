package com.raminq.security.web.api.security;


import com.raminq.security.domain.dto.LoginModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.service.security.AuthService;
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

    @PostMapping("login")
    public UserModel login(@RequestBody @Valid LoginModel loginModel) {
        return authService.login(loginModel);
    }

//    @PostMapping("register")
//    public UserView register(@RequestBody @Valid CreateUserRequest request) {
//        return userService.create(request);
//    }

}
