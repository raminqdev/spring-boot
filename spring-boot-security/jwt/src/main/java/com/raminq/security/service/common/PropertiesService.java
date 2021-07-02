package com.raminq.security.service.common;

import lombok.Getter;

@Getter
public class PropertiesService {
    public final String jwtSecret;
    public final Long jwtExpirationMs;
    public final Long jwtRefreshExpirationMs;

    public PropertiesService(String jwtSecret, Long jwtExpirationMs, Long jwtRefreshExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
    }
}
