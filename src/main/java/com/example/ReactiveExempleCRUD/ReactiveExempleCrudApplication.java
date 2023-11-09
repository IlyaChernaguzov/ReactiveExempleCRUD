package com.example.ReactiveExempleCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ReactiveExempleCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveExempleCrudApplication.class, args);
	}

}
