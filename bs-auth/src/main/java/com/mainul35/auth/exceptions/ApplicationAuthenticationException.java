package com.mainul35.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ApplicationAuthenticationException extends AuthenticationException {
    public ApplicationAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ApplicationAuthenticationException(String message) {
        super(message);
    }
}
