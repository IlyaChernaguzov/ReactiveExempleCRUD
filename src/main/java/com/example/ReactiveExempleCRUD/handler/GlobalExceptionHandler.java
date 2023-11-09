package com.example.ReactiveExempleCRUD.handler;

import com.example.ReactiveExempleCRUD.exception.AppException;
import com.example.ReactiveExempleCRUD.exception.EnumException;
import com.example.ReactiveExempleCRUD.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String REJECTED_FOR_REASON = "An invalid request was rejected for reason: {}";

    /**
     * Handle {@link AppException}
     *
     * @param e        exception
     * @param exchange {@link ServerWebExchange}
     * @return {@link ResponseEntity} with message and status code
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchAppException(AppException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        EnumException exceptions = e.getEnumExceptions();
        var response = new ErrorResponse(
                request.getURI().getPath(),
                exceptions.getHttpStatus().toString(),
                e.getErrorMessage() == null ? exceptions.getErrorMessage() : e.getErrorMessage(),
                LocalDateTime.now());

        log.error(REJECTED_FOR_REASON, response.message());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }
}
