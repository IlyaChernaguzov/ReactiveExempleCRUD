package com.example.ReactiveExempleCRUD.service;

import com.example.ReactiveExempleCRUD.dto.CarDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarService {

    Mono<CarDto> getCar(Integer carId);

    Mono<CarDto> creatCar(CarDto carDto);

    Mono<CarDto> updateCar(Integer carId, CarDto carDto);

    Mono<Void> deleteCar(Integer carId);

    Flux<CarDto> getAllCars();
}
