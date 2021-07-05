package com.raminq.security.configuration.security;

import com.raminq.security.domain.entity.security.User;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.raminq.security.domain.dto.ErrorCodes.USER_DISABLED;
import static com.raminq.security.domain.dto.ErrorCodes.USER_NOT_FOUND_BY_NAME;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepo userRepo;

    public User loadByUsername(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException(String.valueOf(USER_NOT_FOUND_BY_NAME));
        }
        if (!optionalUser.get().isEnabled()) {
            throw new BadCredentialsException(String.valueOf(USER_DISABLED));
        }
        return optionalUser.get();
    }
}
