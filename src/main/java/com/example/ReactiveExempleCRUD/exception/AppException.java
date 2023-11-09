package com.example.ReactiveExempleCRUD.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppException extends RuntimeException{
    private EnumException enumExceptions;
    private HttpStatus httpStatus;
    private String errorMessage;

    public AppException(EnumException enumExceptions) {
        this.enumExceptions = enumExceptions;
        this.httpStatus = enumExceptions.getHttpStatus();
    }

    public AppException(EnumException enumExceptions, String errorMessage) {
        this.enumExceptions = enumExceptions;
        this.httpStatus = enumExceptions.getHttpStatus();
        this.errorMessage = errorMessage;
    }
}
