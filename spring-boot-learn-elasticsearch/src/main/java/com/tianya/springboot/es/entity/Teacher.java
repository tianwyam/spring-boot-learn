package com.tianya.springboot.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(indexName = "teacher_index" , type = "teacher")
public class Teacher {
	
	@Id
	private Long id ;
	
	@Field
	private String name ;
	
	@Field
	private int age ;
	
	@Field
	private String addr ;

}
