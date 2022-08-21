package com.metro.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.metro")
@EntityScan(basePackages = "com.metro.bean")
@EnableJpaRepositories(basePackages = "com.metro.model.persistence")
public class MetroApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetroApplication.class, args);
	}
}