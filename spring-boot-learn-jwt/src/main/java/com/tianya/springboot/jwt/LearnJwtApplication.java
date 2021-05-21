package com.tianya.springboot.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description
 *	主配置类
 * @author TianwYam
 * @date 2021年5月21日上午9:38:12
 */
// 开启 AOP
@EnableAspectJAutoProxy
@SpringBootApplication
public class LearnJwtApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(LearnJwtApplication.class, args);
	}
}
