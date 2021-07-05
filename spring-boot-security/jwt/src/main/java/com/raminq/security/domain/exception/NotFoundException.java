package com.raminq.security.domain.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int errorCode) {
        super(String.valueOf(errorCode));
    }

    public NotFoundException(Class<?> clazz, long id) {
        super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, String name) {
        super(String.format("Entity %s with name %s not found", clazz.getSimpleName(), name));
    }



}
