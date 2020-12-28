package com.tianya.springboot.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LearnValidationApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(LearnValidationApplication.class, args);
	}
}
