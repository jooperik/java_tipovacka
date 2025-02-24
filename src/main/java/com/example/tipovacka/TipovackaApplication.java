package com.example.tipovacka;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class TipovackaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TipovackaApplication.class, args);
	}

}
