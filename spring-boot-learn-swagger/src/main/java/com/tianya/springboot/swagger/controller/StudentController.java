package com.tianya.springboot.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.common.entity.ResultUtis;
import com.tianya.springboot.common.entity.swagger.StudentBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/student")
@Api(tags = "学生管理")
public class StudentController {

	
	@GetMapping("/")
	@ApiOperation(value = "获取单个学生")
	public ResultUtis getStudent() {
		return ResultUtis.success(StudentBean.builder()
				.id(12)
				.name("tianya")
				.addr("成都")
				.age(23)
				.build());
	}
}
