package com.raminq.security.domain.mapper;


import com.raminq.security.domain.dto.RegisterModel;
import com.raminq.security.domain.dto.UserFullModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.dto.UserUpdateModel;
import com.raminq.security.domain.entity.security.Permission;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.exception.NotFoundException;
import com.raminq.security.repository.security.RoleRepo;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.raminq.security.domain.dto.ErrorCodes.ROLE_NOT_FOUND_BY_NAME;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
import static org.springframework.util.StringUtils.hasText;


@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private RoleRepo roleRepo;

    @Mapping(target = "role", ignore = true)
    public abstract UserModel toUserModel(User user);

    @Mapping(target = "role", ignore = true)
    public abstract UserFullModel toUserFullModel(User user);

    @Mapping(target = "role", ignore = true)
    public abstract List<UserFullModel> toUserFullModel(List<User> user);

    @Mapping(target = "role", source = "role", ignore = true)
    public abstract User toUser(RegisterModel model);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "role", ignore = true)
    public abstract void updateUser(UserUpdateModel userUpdateModel, @MappingTarget User user);

    @AfterMapping
    protected void afterToUserModel(User user, @MappingTarget UserModel userModel) {
        if (user.getRole() != null) {
            userModel.setRole(user.getRole().getName());
            userModel.setPermissions(user.getRole().getPermissions().stream().map(Permission::getName)
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }
    }

    @AfterMapping
    protected void afterToUserFullModel(User user, @MappingTarget UserFullModel userFullModel) {
        if (user.getRole() != null) {
            userFullModel.setRole(user.getRole().getName());
            userFullModel.setPermissions(user.getRole().getPermissions().stream().map(Permission::getName)
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }
    }

    @AfterMapping
    protected void afterToUser(RegisterModel registerModel, @MappingTarget User.UserBuilder user) {
        if (hasText(registerModel.getRole())) {
            user.role(roleRepo.findByName(registerModel.getRole())
                    .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND_BY_NAME)));
        }
    }

    @AfterMapping
    protected void afterUpdateUser(UserUpdateModel userUpdateModel, @MappingTarget User user) {
        if (hasText(userUpdateModel.getRole())) {
            user.setRole(roleRepo.findByName(userUpdateModel.getRole())
                    .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND_BY_NAME)));
        }
    }
}
