package com.tianya.springboot.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SwaggerLearnApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SwaggerLearnApplication.class, args);
	}

}
