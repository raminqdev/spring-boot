package com.raminq.security.configuration.properties;

import lombok.Getter;

@Getter
public class Properties {
    public final String jwtSecret;
    public final Long jwtExpirationMs;
    public final Long jwtRefreshExpirationMs;

    public Properties(String jwtSecret, Long jwtExpirationMs, Long jwtRefreshExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
    }
}
