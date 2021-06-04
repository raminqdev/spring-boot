package com.raminq.security.configuration;


import com.raminq.security.domain.dto.enums.Permissions;
import com.raminq.security.domain.dto.enums.Roles;
import com.raminq.security.domain.entity.security.Permission;
import com.raminq.security.domain.entity.security.Role;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.repository.security.PermissionRepo;
import com.raminq.security.repository.security.RoleRepo;
import com.raminq.security.repository.security.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;
    private final PasswordEncoder passwordEncoder;

    private final List<String> usernames = of(
            "admin@gmail.com",
            "manager@gmail.com",
            "user@gmail.com"
    );
    private final List<String> fullNames = of(
            "Ada Lovelace",
            "Jack Willis",
            "Alan Turing"
    );
    private final List<String> roles = of(
            Roles.Admin.toString(),
            Roles.Manager.toString(),
            Roles.User.toString()
    );
    private final List<String> permissions = of(
            Permissions.Read.toString(),
            Permissions.Write.toString(),
            Permissions.Update.toString(),
            Permissions.Delete.toString()
    );
    private final String password = "123";


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        //userRepo.deleteAll();
        //userRepo.deleteAll();
        //userRepo.deleteAll();

        List<Permission> permissionList = new ArrayList<>();
        if (permissionRepo.count() == 0) {
            permissionList = of(
                    Permission.builder().name(Permissions.Read.toString()).build(),
                    Permission.builder().name(Permissions.Write.toString()).build(),
                    Permission.builder().name(Permissions.Update.toString()).build(),
                    Permission.builder().name(Permissions.Delete.toString()).build()
            );

            permissionList = permissionRepo.saveAllAndFlush(permissionList);
        }

        List<Role> roleList = new ArrayList<>();
        if (roleRepo.count() == 0) {
            roleList = of(
                    Role.builder()
                            .name(Roles.Admin.toString())
                            .permissions(permissionList)
                            .build(),

                    Role.builder()
                            .name(Roles.Manager.toString())
                            .permissions(permissionList.stream()
                                    .filter(a -> a.getName() == "Write")
                                    .collect(Collectors.toList()))
                            .build(),

                    Role.builder()
                            .name(Roles.User.toString())
                            .permissions(permissionList.stream()
                                    .filter(a -> a.getName() == "Read")
                                    .collect(Collectors.toList()))
                            .build()
            );

            roleList = roleRepo.saveAllAndFlush(roleList);
        }


        if (userRepo.count() == 0) {
            for (int i = 0; i < usernames.size(); ++i) {
                User user = User.builder()
                        .username(usernames.get(i))
                        .password(passwordEncoder.encode(password))
                        .fullName(fullNames.get(i))
                        .role(roleList.get(i))
                        .build();

                userRepo.save(user);
            }
        }

//        Optional<User> byId = userRepo.findById(18L);
//        System.out.println("-------- --->  " + byId.get());
//        Optional<Permission> byId1 = permissionRepo.findById(11L);
//        User u = User.builder().build();

    }

}
