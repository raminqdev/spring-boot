package com.raminq.security.web.api;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/dummy")
@RequiredArgsConstructor
public class DummyApi {

    @Secured({"Admin"})
    @PostMapping
    public void login() {

    }

}
