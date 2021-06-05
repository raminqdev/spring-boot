package com.raminq.security.domain.mapper;


import com.raminq.security.domain.dto.RegisterModel;
import com.raminq.security.domain.entity.security.User;
import org.mapstruct.*;

import static java.util.stream.Collectors.toSet;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = Long.class)
public abstract class UserEditMapper {

    @Mapping(target = "roleName", ignore = true)
    public abstract User create(RegisterModel model);

//    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
//    @Mapping(target = "authorities", ignore = true)
//    public abstract void update(UpdateUserRequest request, @MappingTarget User user);

//    @AfterMapping
//    protected void afterCreate(CreateUserRequest request, @MappingTarget User user) {
//        if (request.getAuthorities() != null) {
//            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(toSet()));
//        }
//    }

//    @AfterMapping
//    protected void afterUpdate(UpdateUserRequest request, @MappingTarget User user) {
//        if (request.getAuthorities() != null) {
//            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(toSet()));
//        }
//    }

}
