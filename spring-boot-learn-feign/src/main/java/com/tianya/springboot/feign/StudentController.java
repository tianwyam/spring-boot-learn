package com.tianya.springboot.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.common.entity.ResultUtis;
import com.tianya.springboot.feign.client.StudentFeignClient;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentFeignClient studentFeignClient ;

	
	@GetMapping("/list")
	public ResultUtis getList() {
		return studentFeignClient.getList();
	}
	
}
