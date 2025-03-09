package com.example.tipovacka;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@SpringBootApplication
@Configuration
public class TipovackaApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TipovackaApplication.class, args);
	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
}
