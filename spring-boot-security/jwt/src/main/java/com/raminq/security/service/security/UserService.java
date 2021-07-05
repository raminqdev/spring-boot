package com.raminq.security.service.security;

import com.raminq.security.domain.dto.*;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.exception.NotFoundException;
import com.raminq.security.domain.mapper.UserMapper;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import java.util.Optional;

import static com.raminq.security.domain.dto.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserFullModel create(RegisterModel registerModel) {
        if (userRepo.findByUsername(registerModel.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!registerModel.getPassword().equals(registerModel.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }

        User user = userMapper.toUser(registerModel);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        return userMapper.toUserFullModel(user);
    }

    @Transactional
    public UserFullModel update(Long id, UserUpdateModel userUpdateModel) {
        User user = findById(id);
        userMapper.updateUser(userUpdateModel, user);
        user = userRepo.save(user);
        return userMapper.toUserFullModel(user);
    }

    public UserFullModel getUserFullModel(Long id) {
        return userMapper.toUserFullModel(findById(id));
    }

    @Transactional
    public Long delete(Long id) {
        User user = findById(id);

        user.setUsername(user.getUsername().replace("@", String.format("_%s@", user.getId().toString())));
        user.setEnabled(false);
        user = userRepo.save(user);
        return user.getId();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_BY_ID);
        }
        return optionalUser.get();
    }

    public ListResponse<UserFullModel> listUsers(PagingModel pagingModel) {
        Page<User> all = userRepo.findAll(pagingModel.toPageable());

        return new ListResponse<>(userMapper.toUserFullModel(all.getContent()),
                all.getTotalElements(), pagingModel.getPageSize(), pagingModel.getPageNumber());
    }

    public ListResponse<UserFullModel> searchUsers(SearchRequest<SearchUsersQuery> request) {
        PagingModel pagingModel = request.getPagingModel();
        SearchUsersQuery query = request.getQuery();

        Page<User> all = userRepo.search(query.getId(), query.getUsername(),
                query.getFullName(), query.getEnabled(), pagingModel.toPageable());

        return new ListResponse<>(userMapper.toUserFullModel(all.getContent()),
                all.getTotalElements(), pagingModel.getPageSize(), pagingModel.getPageNumber());
    }
}
