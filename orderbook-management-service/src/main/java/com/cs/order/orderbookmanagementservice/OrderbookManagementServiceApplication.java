package com.cs.order.orderbookmanagementservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cs.order.*")
public class OrderbookManagementServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(OrderbookManagementServiceApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(OrderbookManagementServiceApplication.class, args);
	}

	
}
