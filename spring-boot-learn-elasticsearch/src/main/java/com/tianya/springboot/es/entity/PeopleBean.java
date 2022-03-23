package com.tianya.springboot.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "people_index", type = "people")
public class PeopleBean {
	
	/**
	 * 主键
	 */
	@Id
	private Long uid ;
	
	/**
	 * 姓名
	 */
	@Field
	private String name ;
	
	/**
	 * 年龄
	 */
	@Field
	private int age ;
	
	/**
	 * 地址
	 */
	@Field
	private String addr ;
	
	/**
	 * 生日
	 */
	@Field
	private String birthDay ;
	
	/**
	 * 职业
	 */
	@Field
	private String professional ;
	
	/**
	 * 兴趣
	 */
	@Field
	private String interest ;
	
}
