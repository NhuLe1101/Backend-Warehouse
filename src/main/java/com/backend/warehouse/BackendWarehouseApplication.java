package com.backend.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Bật tính năng scheduling
public class BackendWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendWarehouseApplication.class, args);
	}

}
