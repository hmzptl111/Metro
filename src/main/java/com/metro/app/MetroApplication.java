package com.metro.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.metro")
public class MetroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetroApplication.class, args);
	}
}