package com.raminq.security.web.api.security;

import com.raminq.security.domain.dto.RegisterModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.dto.UserUpdateModel;
import com.raminq.security.service.security.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "UserAdmin")
@RestController
@RequestMapping(path = "api/admin/user")
//@RolesAllowed(Role.USER_ADMIN)
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;

    @PostMapping("register")
    public UserModel register(@RequestBody @Valid RegisterModel request) {
        return userService.create(request);
    }

    @PutMapping("{id}")
    public UserModel update(@PathVariable Long id, @RequestBody @Valid UserUpdateModel request) {
        return userService.update(id, request);
    }

//    @DeleteMapping("{id}")
//    public UserView delete(@PathVariable String id) {
//        return userService.delete(new ObjectId(id));
//    }
//
//    //@PostAuthorize("#id == {authentication.principal.id}")  //SpEL
//    @GetMapping("{id}")
//    public UserView get(@PathVariable String id) {
//        System.out.println(generalDataService.getUser());
//        return userService.getUser(new ObjectId(id));
//    }
//
//    @PostMapping("search")
//    public ListResponse<UserView> search(@RequestBody SearchRequest<SearchUsersQuery> request) {
//        return new ListResponse<>(userService.searchUsers(request.getPage(), request.getQuery()));
//    }

}
