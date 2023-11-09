package com.example.ReactiveExempleCRUD.service;

import com.example.ReactiveExempleCRUD.dto.CarDto;
import com.example.ReactiveExempleCRUD.entity.Car;
import com.example.ReactiveExempleCRUD.exception.AppException;
import com.example.ReactiveExempleCRUD.repository.CarRepository;
import com.example.ReactiveExempleCRUD.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(SpringExtension.class)
class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Test
    void getCar_ReturnMonoCarDto_WhenSuccessful(){
        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .kilowatt(2000)
                .build();

        CarDto carDto = new CarDto(1, "BMW", 2000);

        Mockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(car));
        StepVerifier.create(carService.getCar(1))
                .expectSubscription()
                .expectNext(carDto)
                .verifyComplete();

        Mockito.verify(carRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getCar_ReturnMonoError_WhenEmptyMonoIsReturned(){
        Mockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.empty());
        StepVerifier.create(carService.getCar(1))
                .expectSubscription()
                .expectError(AppException.class)
                .verify();
    }

    @Test
    void creatCar_ReturnMonoCarDto_WhenSuccessful(){
        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .kilowatt(2000)
                .build();

        CarDto carDto = new CarDto(null, "BMW", 2000);
        CarDto carAfterSave = new CarDto(1, "BMW", 2000);

        Mockito.when(carRepository.save(ArgumentMatchers.any(Car.class))).thenReturn(Mono.just(car));
        StepVerifier.create(carService.creatCar(carDto))
                .expectSubscription()
                .expectNext(carAfterSave)
//                .expectNextMatches(c -> c.id().equals(1))
                .verifyComplete();

        Mockito.verify(carRepository, Mockito.times(1)).save(ArgumentMatchers.any(Car.class));
    }

    @Test
    void updateCar_ReturnMonoCarDto_WhenSuccessful(){
        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .kilowatt(2000)
                .build();

        Car carAfterUpdate = Car.builder()
                .id(1)
                .brand("BMW")
                .kilowatt(3000)
                .build();

        CarDto carDto = new CarDto(null, "BMW", 2000);
        CarDto carAfterSave = new CarDto(1, "BMW", 3000);

        Mockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(car));
        Mockito.when(carRepository.save(ArgumentMatchers.any(Car.class))).thenReturn(Mono.just(carAfterUpdate));

        StepVerifier.create(carService.updateCar(1, carDto))
                .expectSubscription()
                .expectNext(carAfterSave)
//                .expectNextMatches(c -> c.getKilowatt().equals(carAfterUpdate.getKilowatt()))
                .verifyComplete();

        Mockito.verify(carRepository, Mockito.times(1)).save(ArgumentMatchers.any(Car.class));
    }

    @Test
    void deleteCar_ReturnMonoCarDto_WhenSuccessful(){
        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .kilowatt(2000)
                .build();

        Mockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(car));
        Mockito.when(carRepository.delete(ArgumentMatchers.any(Car.class))).thenReturn(Mono.empty());

        StepVerifier.create(carService.deleteCar(1))
                .expectSubscription()
                .verifyComplete();

        Mockito.verify(carRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getAllCars_ReturnFluxCarDto_WhenSuccessful(){
        Car car1 = Car.builder()
                .id(1)
                .brand("BMW")
                .kilowatt(2000)
                .build();

        Car car2 = Car.builder()
                .id(2)
                .brand("Volvo")
                .kilowatt(1500)
                .build();

        CarDto carDto1 = new CarDto(1, "BMW", 2000);
        CarDto carDto2 = new CarDto(2, "Volvo", 1500);


        Mockito.when(carRepository.findAll()).thenReturn(Flux.just(car1, car2));

        StepVerifier.create(carService.getAllCars())
                .expectSubscription()
                .expectNext(carDto1)
                .expectNext(carDto2)
//                .expectNextMatches(c -> c.getKilowatt().equals(carDto1.getKilowatt()))
//                .expectNextMatches(c -> c.getKilowatt().equals(carDto2.getKilowatt()))
                .verifyComplete();

        Mockito.verify(carRepository, Mockito.times(1)).findAll();
    }
}
