package com.tianya.springboot.excel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.excel.entity.ExcelUser;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@RestController
@RequestMapping("/api/v1/excel")
public class ExcelUserController {

	
	@GetMapping("/read")
	public void getReadExcel(String excelFilePath) {
		
		ExcelReader reader = ExcelUtil.getReader(excelFilePath);
//		reader.addHeaderAlias("用户ID", "id");
//		reader.addHeaderAlias("用户名称", "userName");
//		reader.addHeaderAlias("地址", "addr");
//		reader.addHeaderAlias("身高", "height");
		
		List<ExcelUser> users = reader.readAll(ExcelUser.class);
		users.forEach(System.out::println);
	}
	
	
	
}
