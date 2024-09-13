package com.credit.infrastructure.controller;

import com.credit.infrastructure.controller.dto.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Objects;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        final FieldError fieldError = ex.getBindingResult().getFieldError();
        final String message = String.format("%s - %s", Objects.requireNonNull(fieldError).getField(), fieldError.getDefaultMessage());
        return buildErrorResponse(message, request, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        log.error("Missing part: {}", ex.getMessage());
        return buildErrorResponse("Required part is missing: " + ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGlobalException(final Exception ex, final WebRequest request) {
        log.error(ex.getMessage());
        return buildErrorResponse(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(final String message, final WebRequest request, final HttpStatus status) {
        final ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(Instant.now())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }
}