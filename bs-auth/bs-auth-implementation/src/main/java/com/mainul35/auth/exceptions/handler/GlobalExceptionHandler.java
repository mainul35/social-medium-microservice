package com.mainul35.auth.exceptions.handler;

import com.mainul35.auth.exceptions.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BAD_CREDENTIALS = "bad_credentials";
    private static final String CONFLICT = "conflict";

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsExceptions(final BadCredentialsException e, final WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setErrorIdentifier(BAD_CREDENTIALS);

        log.error(String.valueOf(errorResponse), e);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handlePSQLExceptions(final DuplicateKeyException e, final WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Duplicate entry found in database: " + e.getMessage());
        errorResponse.setErrorIdentifier(CONFLICT);
        log.error(String.valueOf(errorResponse), e);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }
}
