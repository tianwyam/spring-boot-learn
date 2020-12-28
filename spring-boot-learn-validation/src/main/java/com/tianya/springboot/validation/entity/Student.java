package com.tianya.springboot.validation.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import com.tianya.springboot.validation.type.ValidType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
	
	@NotBlank(message = "学号不能为空", groups = {ValidType.Update.class, ValidType.Delete.class})
	private int id ;
	
	@NotBlank(message = "学生姓名不可以为空", groups = {ValidType.Insert.class})
	private String name ;
	
	private String addr ;
	
	@Range(max = 300, message = "年龄不能超过300")
	private int age ;
	
	private int height ;
	
	private String phone ;
	
	private String idcard ;
	
	@Email(message = "邮箱格式错误")
	private String email ;

}
