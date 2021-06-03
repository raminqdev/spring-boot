package com.raminq.security.service.security;

import com.raminq.security.domain.entity.security.User;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
//        if (!request.getPassword().equals(request.getRePassword())) {
//            throw new ValidationException("Passwords don't match!");
//        }
        if (user.getAuthorities() == null) {
            user.setAuthorities(new HashSet<>());
        }

//        User user = userEditMapper.create(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepo.save(user);

        return user;
    }


    public long count() {
        return userRepo.count();
    }
}
