package br.com.lsargus.testesicredi.common.exception;

import br.com.lsargus.testesicredi.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    ResponseEntity<ApiError> conflict(ConflictException e, HttpServletRequest r) {
        return error(HttpStatus.CONFLICT.value(), "CONFLICT", e.getMessage(), r);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiError> notFound(NotFoundException e, HttpServletRequest r) {
        return error(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", e.getMessage(), r);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ApiError> auth(BadCredentialsException e, HttpServletRequest r) {
        return error(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", "Credenciais invalidas", r);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> invalid(MethodArgumentNotValidException e, HttpServletRequest r) {
        var msg = Arrays.stream(e.getDetailMessageArguments())
                .map(String.class::cast).filter(m -> !m.isBlank())
                .findFirst()
                .orElse("Payload invalido");
        return error(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR", msg, r);
    }

    @ExceptionHandler(VoteAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleVoteAlreadyRegistered(
            VoteAlreadyRegisteredException e,
            HttpServletRequest r) {

        return error(HttpStatus.CONFLICT.value(), "VALIDATION_ERROR", e.getMessage(), r);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleVoteAlreadyRegistered(
            IllegalStateException e,
            HttpServletRequest r) {

        return error(HttpStatus.CONFLICT.value(), "ILLEGAL_STATE", e.getMessage(), r);
    }

    private ResponseEntity<ApiError> error(int status, String code, String msg, HttpServletRequest r) {
      var error = new ApiError().timestamp(OffsetDateTime.now()).status(status).code(code).message(msg).path(r.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
