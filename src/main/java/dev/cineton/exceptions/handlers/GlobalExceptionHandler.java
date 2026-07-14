package dev.cineton.exceptions.handlers;

import dev.cineton.dto.response.ErrorResponse;
import dev.cineton.exceptions.AuthenticationException;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication Error",
                ex.getMessage(), request);
    }

    @ExceptionHandler(CreateEntityException.class)
    public ResponseEntity<ErrorResponse> handleCreateEntityException(CreateEntityException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Create Entity Error",
                ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String title, String message, HttpServletRequest request) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(status.value(), title, message,
                        request.getRequestURI(), LocalDateTime.now())
        );
    }
}