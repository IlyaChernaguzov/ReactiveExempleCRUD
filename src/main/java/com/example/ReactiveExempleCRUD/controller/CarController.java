package com.example.ReactiveExempleCRUD.controller;

import com.example.ReactiveExempleCRUD.dto.CarDto;
import com.example.ReactiveExempleCRUD.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @GetMapping("/{carId}")
    Mono<CarDto> getCar(@PathVariable("carId") Integer carId){
        return carService.getCar(carId);
    }

    @PostMapping
    Mono<CarDto> creatCar(@RequestBody CarDto carDto){
        return carService.creatCar(carDto);
    }

    @PatchMapping("/{carId}")
    Mono<CarDto> updateCar(@PathVariable("carId") Integer carId, @RequestBody CarDto carDto){
        return carService.updateCar(carId, carDto);
    }

    @DeleteMapping("/{carId}")
    Mono<Void> deleteCar(@PathVariable("carId") Integer carId){
        return carService.deleteCar(carId);
    }

    @GetMapping("/all")
    Flux<CarDto> getAllCars(){
        return carService.getAllCars();
    }
}
