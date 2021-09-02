package com.tianya.springboot.redis.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description
 *	学生实体类
 * @author TianwYam
 * @date 2021年9月2日下午6:54:09
 */
@Data
@Builder
public class StudentBean {
	
	private int id ;
	
	private String name ;
	
	private double score ;
	

}
