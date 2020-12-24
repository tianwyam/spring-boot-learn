package com.tianya.springboot.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.common.entity.ResultUtis;
import com.tianya.springboot.common.entity.swagger.StudentBean;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

	
	@GetMapping("/list")
	public ResultUtis getList() {
		
		List<StudentBean> studentBeans = new ArrayList<>();
		Random random = new Random();
		int nextInt = random.nextInt(10);
		for (int i = 0 ; i < nextInt ; i++) {
			StudentBean student = StudentBean.builder().id(i).name("Stud"+i).age(24+i).addr("成都"+i).build();
			studentBeans.add(student);
		}
		return ResultUtis.success(studentBeans);
	}
	
}
