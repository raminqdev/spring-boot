package com.raminq.security.web.api.security;

import com.raminq.security.configuration.aspect.PermissionAuthorize;
import com.raminq.security.domain.dto.*;
import com.raminq.security.domain.dto.enums.PermissionLevel;
import com.raminq.security.service.security.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/admin/user")
//@RolesAllowed(Role.USER_ADMIN)
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;

    @PostMapping("register")
    public UserFullModel register(@RequestBody @Valid RegisterModel request) {
        return userService.create(request);
    }

    @PutMapping("{id}")
    public UserFullModel update(@PathVariable Long id, @RequestBody @Valid UserUpdateModel request) {
        return userService.update(id, request);
    }

    @GetMapping("{id}")
    public UserFullModel get(@PathVariable Long id) {
        return userService.getUserFullModel(id);
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        return userService.delete(id);
    }


    @PermissionAuthorize(1)
    @PostMapping("list")
    public ListResponse<UserFullModel> List(@RequestBody @Valid PagingModel pagingModel) {
        return userService.listUsers(pagingModel);
    }

    @PostMapping("search")
    public ListResponse<UserFullModel> search(@RequestBody @Valid SearchRequest<SearchUsersQuery> request) {
        return (userService.searchUsers(request));
    }

}
