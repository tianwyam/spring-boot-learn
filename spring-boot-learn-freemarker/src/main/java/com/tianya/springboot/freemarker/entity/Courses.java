package com.tianya.springboot.freemarker.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 
 * 课程实体类
 * @author: TianwYam
 * @date 2021年5月4日 上午9:03:53
 */
@Data
@Builder
public class Courses {
	
	// 课程编号
	private int courseId ;
	
	// 课程名称
	private String courseName ;
	
	// 课程学分
	private float courseScore ;

}
