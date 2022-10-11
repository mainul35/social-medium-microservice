package com.mainul35.socialmedium.config.exceptions;

import lombok.Data;

@Data
public class UnauthorizedException extends RuntimeException{
    private String message;
    public UnauthorizedException(String unauthorized) {
        this.message = unauthorized;
    }
}
