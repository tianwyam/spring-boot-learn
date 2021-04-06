package com.tianya.springboot.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tianya.springboot.common.entity.ResultUtis;
import com.tianya.springboot.feign.client.StudentFeignClient;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentFeignClient studentFeignClient ;

	@Autowired
	private RestTemplate restTemplate ;
	
	@Value("${student.feign.url}")
	private String studentServerUrl ;
	
	@GetMapping("/list")
	public ResultUtis getList() {
		
		
//		return studentFeignClient.getList();
		

		// 类似
		return restTemplate.getForObject(studentServerUrl + "/list", ResultUtis.class);
		
	}
	
}
