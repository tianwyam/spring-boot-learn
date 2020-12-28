package com.tianya.springboot.validation.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.common.entity.ResultUtis;
import com.tianya.springboot.validation.entity.Student;
import com.tianya.springboot.validation.type.ValidType;

@RestController
@RequestMapping("/student")
public class StudentController {

	
	
	@PostMapping("/")
	public ResultUtis add(
			@RequestBody 
			@Validated(ValidType.Insert.class) 
			Student student) {
		
		return ResultUtis.success(student);
	}
	
	
	
}
