package com.tianya.springboot.capture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaptureScreenApplication {

	public static void main(String[] args) {
		
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(CaptureScreenApplication.class, args);	
	}
	
}
