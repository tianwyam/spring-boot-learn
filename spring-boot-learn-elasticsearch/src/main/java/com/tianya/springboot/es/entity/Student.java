package com.tianya.springboot.es.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
	
	private long id ;
	
	private String name;
	
	private int age ;
	
	private String addr;

}
