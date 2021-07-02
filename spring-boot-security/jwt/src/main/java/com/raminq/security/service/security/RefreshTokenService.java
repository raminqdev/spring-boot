package com.raminq.security.service.security;

import com.raminq.security.service.common.PropertiesService;
import com.raminq.security.configuration.security.JwtTokenUtil;
import com.raminq.security.domain.dto.RefreshTokenResponse;
import com.raminq.security.domain.entity.security.RefreshToken;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.domain.exception.TokenRefreshException;
import com.raminq.security.repository.security.RefreshTokenRepo;
import com.raminq.security.repository.security.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static com.raminq.security.domain.dto.ErrorCodes.REFRESH_TOKEN_IS_NOT_IN_DATABASE;
import static com.raminq.security.domain.dto.ErrorCodes.REFRESH_TOKEN_WAS_EXPIRED;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepo userRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepo refreshTokenRepo;
    private final PropertiesService propertiesService;


    @Transactional
    public String generateRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(propertiesService.getJwtRefreshExpirationMs()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken.getToken();
    }

    @Transactional
    public RefreshTokenResponse generateNewRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByToken(refreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user ->
                        {
                            String token = jwtTokenUtil.generateAccessToken(user);
                            return new RefreshTokenResponse(token, refreshToken);
                        }
                ).orElseThrow(() -> new TokenRefreshException(REFRESH_TOKEN_IS_NOT_IN_DATABASE));
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(REFRESH_TOKEN_WAS_EXPIRED);
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepo.deleteByUser(userRepo.findById(userId).get());
    }
}
