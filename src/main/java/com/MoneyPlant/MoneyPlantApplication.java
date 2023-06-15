package com.MoneyPlant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyPlantApplication {
	private static final Logger logger = LoggerFactory.getLogger(MoneyPlantApplication.class);


	public static void main(String[] args) {
		logger.info("Logging configuration: {}", System.getProperty("logback.configurationFile"));
		// Rest of your application initialization code
	}
}
