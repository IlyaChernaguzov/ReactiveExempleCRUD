package com.example.ReactiveExempleCRUD.service.impl;

import com.example.ReactiveExempleCRUD.dto.CarDto;
import com.example.ReactiveExempleCRUD.entity.Car;
import com.example.ReactiveExempleCRUD.exception.AppException;
import com.example.ReactiveExempleCRUD.exception.EnumException;
import com.example.ReactiveExempleCRUD.repository.CarRepository;
import com.example.ReactiveExempleCRUD.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    @Override
    public Mono<CarDto> getCar(Integer carId) {
        return carRepository.findById(carId)
                .map(car -> new CarDto(car.getId(), car.getBrand(), car.getKilowatt()))
                .switchIfEmpty(Mono.error(new AppException(EnumException.NOT_FOUND)));
    }

    @Override
    public Mono<CarDto> creatCar(CarDto carDto) {
        return carRepository.save(Car.builder()
                        .brand(carDto.brand())
                        .kilowatt(carDto.kilowatt())
                        .build())
                        .map(car -> new CarDto(car.getId(), car.getBrand(), car.getKilowatt()));
    }

    @Override
    public Mono<CarDto> updateCar(Integer carId, CarDto carDto) {
        return carRepository.findById(carId)
                .doOnNext(car -> {
                    car.setBrand(carDto.brand());
                    car.setKilowatt(carDto.kilowatt());
                })
                .flatMap(carRepository::save)
                .map(car -> new CarDto(car.getId(), car.getBrand(), car.getKilowatt()))
                .switchIfEmpty(Mono.error(new AppException(EnumException.NOT_FOUND)));
    }

    @Override
    public Mono<Void> deleteCar(Integer carId) {
        return carRepository.findById(carId)
                .switchIfEmpty(Mono.error(new AppException(EnumException.NOT_FOUND)))
                .flatMap(carRepository::delete);
    }

    @Override
    public Flux<CarDto> getAllCars(){
        return carRepository.findAll()
                .map(car -> new CarDto(car.getId(), car.getBrand(), car.getKilowatt()))
                .switchIfEmpty(Mono.error(new AppException(EnumException.NOT_FOUND)));//TODO: справь dfdsfdsf dsfsdfsf

        //TODO:vgdfgfdg
    }
}
