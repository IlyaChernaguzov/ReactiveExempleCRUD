package com.example.ReactiveExempleCRUD.controller;

import com.example.ReactiveExempleCRUD.dto.CarDto;
import com.example.ReactiveExempleCRUD.exception.AppException;
import com.example.ReactiveExempleCRUD.exception.EnumException;
import com.example.ReactiveExempleCRUD.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CarController.class)
public class CarControllerTest {
    @Autowired
    WebTestClient webClient;
    @MockBean
    CarService carService;

    @Test
    void getCar_And_Return_200_OK() {
        CarDto carDto = new CarDto(1, "BMW", 2000);

        when(carService.getCar(ArgumentMatchers.anyInt())).thenReturn(Mono.just(carDto));

        webClient.get()
                .uri("/car/{carId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .isEqualTo(carDto);
    }

    @Test
    void getCar_And_Return_404_NOT_FOUND() {
        when(carService.getCar(ArgumentMatchers.anyInt())).thenReturn(Mono.error(new AppException(EnumException.NOT_FOUND)));

        webClient.get()
                .uri("/car/{carId}", 1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void creatCar_And_Return_200_OK() {
        CarDto carDto = new CarDto(1, "BMW", 2000);

        when(carService.creatCar(ArgumentMatchers.any(CarDto.class))).thenReturn(Mono.just(carDto));

        webClient.post()
                .uri("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CarDto(null, "BMW", 2000))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .isEqualTo(carDto);
    }

    @Test
    void updateCar_And_Return_200_OK() {
        CarDto carDto = new CarDto(1, "BMW", 2000);

        when(carService.updateCar(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CarDto.class))).thenReturn(Mono.just(carDto));

        webClient.patch()
                .uri("/car/{carId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CarDto(null, "BMW", 2000))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .isEqualTo(carDto);
    }

    @Test
    void deleteCar_And_Return_200_OK() {
        when(carService.deleteCar(ArgumentMatchers.anyInt())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/car/{carId}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .isEmpty();
    }

    @Test
    void getAllCars_And_Return_200_OK() {
        CarDto carDto1 = new CarDto(1, "BMW", 2000);
        CarDto carDto2 = new CarDto(2, "Volvo", 1500);

        when(carService.getAllCars()).thenReturn(Flux.just(carDto1, carDto2));

        webClient.get()
                .uri("/car/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].brand").isEqualTo("BMW")
                .jsonPath("$[1].kilowatt").isEqualTo(1500);
    }
}
