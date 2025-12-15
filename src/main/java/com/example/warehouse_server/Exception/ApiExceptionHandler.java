package com.example.warehouse_server.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    public record ApiError(Instant timestamp, int status, String message) {}

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handle(ResponseStatusException ex) {
        int status = ex.getStatusCode().value();
        String msg = (ex.getReason() == null || ex.getReason().isBlank())
                ? "Некорректный запрос"
                : ex.getReason();

        return ResponseEntity
                .status(status)
                .body(new ApiError(Instant.now(), status, msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAny(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ApiError(Instant.now(), 500, "Внутренняя ошибка сервера: " + ex.getMessage()));
    }
}
