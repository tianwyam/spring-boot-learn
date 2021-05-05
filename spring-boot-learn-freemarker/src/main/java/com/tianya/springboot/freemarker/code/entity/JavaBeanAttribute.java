package com.tianya.springboot.freemarker.code.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 
 * Java Bean的属性
 * @author: TianwYam
 * @date 2021年5月5日 上午9:15:01
 */
@Data
@Builder
public class JavaBeanAttribute {
	
	// 属性名称
	private String attrName ;
	
	// 类型
	private String attrType ;
	
	// 注释
	private String attrComment;
	
}
