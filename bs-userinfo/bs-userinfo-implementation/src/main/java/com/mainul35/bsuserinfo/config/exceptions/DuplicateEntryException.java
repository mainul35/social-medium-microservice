package com.mainul35.bsuserinfo.config.exceptions;

import lombok.Data;

@Data
public class DuplicateEntryException extends RuntimeException {

    private String message;
    public DuplicateEntryException(String message) {
        this.message = message;
    }
}
