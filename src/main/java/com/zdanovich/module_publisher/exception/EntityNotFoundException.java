package com.zdanovich.module_publisher.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
            super(message);
        }
}
