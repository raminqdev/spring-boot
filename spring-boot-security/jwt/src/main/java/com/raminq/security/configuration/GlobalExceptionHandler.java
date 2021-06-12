package com.raminq.security.configuration;


import com.raminq.security.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.raminq.security.domain.dto.ErrorCodes.*;
import static org.springframework.util.StringUtils.hasText;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger();

    //----- Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiCallError<String>> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        logger.error("handleNotFoundException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiCallError<>("Not found exception", List.of(ex.getMessage())));
    }

    //----- Validation
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiCallError<String>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        logger.error("ValidationException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Validation exception", List.of(ex.getMessage())));
    }

    //----- Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiCallError<String>> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logger.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex);

        List<String> details = ex.getBindingResult().getFieldErrors().stream().map(fieldError ->
                fieldError.getField() + " " + fieldError.getDefaultMessage()).collect(Collectors.toList());

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Method argument validation failed", details));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiCallError<String>> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        logger.error("handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Missing request parameter", List.of(ex.getMessage())));
    }

    //----- Authentication
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiCallError<Integer>> handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        logger.error("handleAuthenticationException {}\n", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiCallError<>("Unauthorized!",
                        hasText(ex.getMessage()) ? List.of(Integer.valueOf(ex.getMessage())) : List.of(UN_AUTHORIZED)));
    }

    //----- Authorization
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiCallError<Integer>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        logger.error("handleAccessDeniedException {}\n", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiCallError<>("Access denied!",
                        hasText(ex.getMessage()) ? List.of(Integer.valueOf(ex.getMessage())) : List.of(ACCESS_DENIED)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiCallError<Integer>> handleInternalServerError(HttpServletRequest request, Exception ex) {
        logger.error("handleInternalServerError {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiCallError<>("Internal server error", List.of(INTERNAL_SERVER_ERROR)));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiCallError<T> {
        private String message;
        private List<T> details = null;
    }

}



