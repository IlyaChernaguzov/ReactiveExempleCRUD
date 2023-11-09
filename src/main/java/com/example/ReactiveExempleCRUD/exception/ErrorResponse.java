package com.example.ReactiveExempleCRUD.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(String uri,
                            String type,
                            String message,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
                            LocalDateTime timeStamp) {
}
