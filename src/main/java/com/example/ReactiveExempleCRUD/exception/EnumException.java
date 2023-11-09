package com.example.ReactiveExempleCRUD.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum EnumException {
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "Страница не найдена",
            "notFoundException");

    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final String exception;
}
