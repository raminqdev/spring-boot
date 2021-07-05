package com.raminq.security.domain.exception;

import org.springframework.security.access.AccessDeniedException;

public class TokenRefreshException extends AccessDeniedException {

    public TokenRefreshException(int errorCode) {
        super(String.valueOf(errorCode));
    }
}
