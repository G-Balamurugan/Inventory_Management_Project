package com.quinbay.orderservicesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class OrderServicesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServicesManagementApplication.class, args);
	}

}
