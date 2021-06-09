package com.raminq.security.service.security;

import com.raminq.security.domain.dto.RegisterModel;
import com.raminq.security.domain.dto.UserModel;
import com.raminq.security.domain.dto.UserUpdateModel;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.exception.NotFoundException;
import com.raminq.security.domain.mapper.UserMapper;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import static com.raminq.security.domain.dto.ErrorCodes.UserNotFoundById;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserModel create(RegisterModel registerModel) {
        if (userRepo.findByUsername(registerModel.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!registerModel.getPassword().equals(registerModel.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }

        User user = userMapper.toUser(registerModel);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        return userMapper.toUserModel(user);
    }


    @Transactional
    public UserModel update(Long id, UserUpdateModel userUpdateModel) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException(UserNotFoundById));
        userMapper.updateUser(userUpdateModel, user);
        user = userRepo.save(user);
        return userMapper.toUserModel(user);
    }
}
