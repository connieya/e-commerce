package com.example.hanghaeplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaePlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanghaePlusApplication.class, args);
	}

}
