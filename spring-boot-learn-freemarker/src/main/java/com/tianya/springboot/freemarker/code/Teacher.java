package com.tianya.springboot.freemarker.code ;

import java.util.Date ;

import lombok.Data ;


@Data
public class Teacher {

	
		// 主键
		private int id ;
		
	
		// 教师姓名
		private String name ;
		
	
		// 性别
		private int sex ;
		
	
		// 出生日期
		private Date birthDate ;
		


}