package com.mainul35.auth.exceptions.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private String code;
    private String errorIdentifier;
    private List<ErrorResponse> rootCause;
}
