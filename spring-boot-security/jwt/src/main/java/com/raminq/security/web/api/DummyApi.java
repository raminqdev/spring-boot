package com.raminq.security.web.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dummy")
@RestController
@RequestMapping(path = "api/dummy")
@RequiredArgsConstructor
public class DummyApi {

    @Secured({"Admin","Manager"})
    @PostMapping
    public String secured() {
        return "success";
    }

    @PreAuthorize("hasAnyRole('Admin','Manager')")
    @PostMapping("/preAuth")
    public String preAuth() {
        return "success";
    }

    @PreAuthorize("hasAuthority('Read')")
    @PostMapping("/dummy1")
    public String dummy1() {
        return "success";
    }
}
