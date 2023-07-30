package com.example.cinema.exception;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
