package com.example.cinema.exception;

public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String errorMessage) {
        super(errorMessage);
    }
}
