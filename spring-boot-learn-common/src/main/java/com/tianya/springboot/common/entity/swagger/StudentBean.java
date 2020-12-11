package com.tianya.springboot.common.entity.swagger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentBean {

	private int id ;
	
	private String name;
	
	private String addr ;
	
	private int age ;
	
}
