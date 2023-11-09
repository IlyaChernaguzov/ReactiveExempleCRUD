package com.example.ReactiveExempleCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
public record CarDto (
    Integer id,
    String brand,
    Integer kilowatt
) {}
