package br.com.lsargus.testesicredi.common.exception;

import br.com.lsargus.testesicredi.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    ResponseEntity<ApiError> conflict(ConflictException e, HttpServletRequest r) {
        return error(409, "CONFLICT", e.getMessage(), r);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiError> notFound(NotFoundException e, HttpServletRequest r) {
        return error(404, "NOT_FOUND", e.getMessage(), r);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ApiError> auth(BadCredentialsException e, HttpServletRequest r) {
        return error(401, "UNAUTHORIZED", "Credenciais invalidas", r);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> invalid(MethodArgumentNotValidException e, HttpServletRequest r) {
        var msg = Arrays.stream(e.getDetailMessageArguments())
                .map(String.class::cast).filter(m -> !m.isBlank())
                .findFirst()
                .orElse("Payload invalido");
        return error(400, "VALIDATION_ERROR", msg, r);
    }

    private ResponseEntity<ApiError> error(int status, String code, String msg, HttpServletRequest r) {
      var error = new ApiError().timestamp(OffsetDateTime.now()).status(status).code(code).message(msg).path(r.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
