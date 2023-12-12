package com.quinbay.inventoryservicesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InventoryServicesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServicesManagementApplication.class, args);
	}

}
