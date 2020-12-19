package com.tianya.springboot.swagger.test.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/v1/test")
@Api(tags = "测试API文档")
public class TestController {

	
	@GetMapping("/")
	@ApiOperation(value = "获取")
	public String test() {
		return "test" ;
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "列表")
	public String test1() {
		return "list" ;
	}
	
	@PostMapping("/post")
	@ApiOperation(value = "POST提交")
	public String test2() {
		return "post" ;
	}
	
	@DeleteMapping("/del")
	@ApiOperation(value = "删除")
	public String test3() {
		return "delete" ;
	}
	
	@PutMapping("/put")
	@ApiOperation(value = "修改")
	public String put() {
		return "put" ;
	}
	
	
}
