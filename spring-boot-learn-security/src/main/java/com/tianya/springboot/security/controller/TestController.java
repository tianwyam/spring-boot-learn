package com.tianya.springboot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 *	测试控制器
 * @author TianwYam
 * @date 2021年4月17日下午1:49:05
 */
@RestController
public class TestController {
	
	@GetMapping("/test")
	public String test() {
		return "test" ;
	}
	
	@GetMapping("/mustLogin")
	public String mustLogin() {
		return "mustLogin" ;
	}
}
