package com.mainul35.socialmedium.config.exceptions;

import com.mainul35.socialmedium.exceptions.NoContentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String VALIDATION_ERROR = "validation_error";
    public static final String NO_CONTENT = "no_content";

    @ExceptionHandler(value = {NoContentException.class})
    protected Mono<ServerResponse> handleLimitReached(NoContentException ex) {
        ErrorResponse response = new ErrorResponse(NO_CONTENT, ex.getMessage());
        this.printStackTrace(ex);
        return Mono.just(response).flatMap(errorResponse -> ServerResponse.badRequest().bodyValue(errorResponse));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected Mono<ServerResponse> handleLimitReached(ConstraintViolationException ex) {
        ErrorResponse response = new ErrorResponse(NO_CONTENT, ex.getMessage());
        this.printStackTrace(ex);
        return Mono.just(response).flatMap(errorResponse -> ServerResponse.badRequest().bodyValue(errorResponse));
    }

    /**
     * Handle TypeMismatchException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MethodArgumentNotValidException.class
     * @param headers HttpHeaders
     * @return the ErrorResponse object
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected Mono<ServerResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            io.netty.handler.codec.http.HttpHeaders headers) {
        ErrorResponse response = new ErrorResponse(VALIDATION_ERROR, "Request is not valid");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ItemValidationError> validationErrors = new LinkedList<>();
        fieldErrors.forEach((v) -> {
            validationErrors.add(new ItemValidationError(v.getObjectName(), v.getField(), v.getRejectedValue(), v.getDefaultMessage()));
        });
        response.setErrorItems(validationErrors);
        return Mono.just(response).flatMap(errorResponse -> ServerResponse.badRequest().bodyValue(errorResponse));
    }

    private void printStackTrace(Exception ex) {
        StackTraceElement[] trace = ex.getStackTrace();
        StringBuilder traceLines = new StringBuilder();
        traceLines.append("Caused By: ").append(ex.fillInStackTrace()).append("\n");
        Arrays.stream(trace).filter(f -> f.getClassName().contains("com.mainul35"))
                .forEach(traceElement -> traceLines.append("\tat ").append(traceElement).append("\n"));
        log.error(traceLines.toString());
    }
}
