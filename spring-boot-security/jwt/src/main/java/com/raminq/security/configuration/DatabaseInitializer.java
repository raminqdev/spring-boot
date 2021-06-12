package com.raminq.security.configuration;


import com.raminq.security.domain.dto.enums.PermissionLevel;
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
    private final String password = "123";


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

//        userRepo.deleteAll();
//        roleRepo.deleteAll();
//        permissionRepo.deleteAll();

        List<Permission> permissionList = new ArrayList<>();
        if (permissionRepo.count() == 0) {
            permissionList = of(
                    Permission.builder().name(PermissionLevel.PANEL_ADMIN.toString()).build(),
                    Permission.builder().name(PermissionLevel.READ.toString()).build(),
                    Permission.builder().name(PermissionLevel.WRITE.toString()).build(),
                    Permission.builder().name(PermissionLevel.UPDATE.toString()).build(),
                    Permission.builder().name(PermissionLevel.DELETE.toString()).build()
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
                                    .filter(a -> a.getName().equals("WRITE") || a.getName().equals("READ"))
                                    .collect(Collectors.toList()))
                            .build(),

                    Role.builder()
                            .name(Roles.User.toString())
                            .permissions(permissionList.stream()
                                    .filter(a -> a.getName().equals("READ"))
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


//        for (int i = 0; i < 1000; ++i) {
//            User user = User.builder()
//                    .username("user" + i)
//                    .password(passwordEncoder.encode(password + i))
//                    .fullName("ramin " + i)
//                    .build();
//
//            userRepo.save(user);
//        }

//        Optional<User> byId = userRepo.findById(18L);
//        System.out.println("-------- --->  " + byId.get());
//        Optional<Permission> byId1 = permissionRepo.findById(11L);
//        User u = User.builder().build();

    }

}
