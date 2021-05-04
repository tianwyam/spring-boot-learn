package com.tianya.springboot.freemarker.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 
 * 学生类实体
 * @author: TianwYam
 * @date 2021年5月4日 上午8:46:31
 */
@Data
@Builder
public class Student {
	
	// 学号
	private long id ;
	
	// 学生名称
	private String name ;
	
	// 学生年龄
	private int age ;
	
	// 学生身高
	private int height ;
	
	// 学生性别(0:女 1:男)
	private int sex ;
	
	// 地址
	private String address ;

	// 所选课程
	private List<Courses> courses ;
}
