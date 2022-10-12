package com.mainul35.auth.exceptions;

public class InvalidTokenException extends RuntimeException {

    private final String message;
    private Exception exception;

    public InvalidTokenException(String message, RuntimeException e) {
        this(message);
        this.exception = e;
    }

    public InvalidTokenException(String message) {
        this.message = message;
    }
}
