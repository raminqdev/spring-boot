package com.raminq.security.configuration;


import com.raminq.security.domain.dto.enums.Role;
import com.raminq.security.domain.entity.security.Authority;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.repository.security.UserRepo;
import com.raminq.security.service.security.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final List<String> usernames = List.of(
            "admin@gmail.com",
            "manager@gmail.com",
            "user@gmail.com"
    );
    private final List<String> fullNames = List.of(
            "Ada Lovelace",
            "Jack Willis",
            "Alan Turing"
    );
    private final List<String> roles = List.of(
            Role.Admin.toString(),
            Role.Manager.toString(),
            Role.User.toString()
    );
    private final String password = "123";

    private final UserService userService;

    public DatabaseInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (userService.count() == 0)
            for (int i = 0; i < usernames.size(); ++i) {
                User user = new User();
                user.setUsername(usernames.get(i));
                user.setPassword(password);
                user.setAuthorities(Set.of(Authority.builder().authority(roles.get(i)).build()));
                user.setFullName(fullNames.get(i));


                userService.create(user);
            }


    }

}
