package com.example.demo.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.rozidan.springboot.logger.EnableLogger;

@SpringBootApplication
@EnableLogger
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
