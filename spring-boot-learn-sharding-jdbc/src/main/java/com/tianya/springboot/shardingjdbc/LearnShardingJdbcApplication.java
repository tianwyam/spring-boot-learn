package com.tianya.springboot.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tianya.springboot.shardingjdbc.mapper")
public class LearnShardingJdbcApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LearnShardingJdbcApplication.class, args);
	}

}
