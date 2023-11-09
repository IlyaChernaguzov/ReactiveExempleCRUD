package com.example.ReactiveExempleCRUD.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@Table("car")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {

    @Id
    Integer id;
    String brand;
    Integer kilowatt;

}
