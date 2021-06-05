package com.raminq.security.service.security;

import com.raminq.security.domain.dto.RegisterModel;
import com.raminq.security.domain.entity.security.Role;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.exception.NotFoundException;
import com.raminq.security.domain.mapper.UserEditMapper;
import com.raminq.security.repository.security.RoleRepo;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserEditMapper userEditMapper;

    @Transactional
    public User create(RegisterModel model) {
        if (userRepo.findByUsername(model.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!model.getPassword().equals(model.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if (model.getRoleName() != null) {
            roleRepo.findByName(model.getRoleName())
                    .orElseThrow(() -> new NotFoundException(model.getRoleName()));
        }

        User user = userEditMapper.create(model);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepo.save(user);

        return user;
    }


    public long count() {
        return userRepo.count();
    }

}
