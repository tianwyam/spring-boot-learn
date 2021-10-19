package com.tianya.springboot.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;


/**
 * @description
 *	公共类型配置表
 * @author TianwYam
 * @date 2021年10月19日下午8:47:36
 */
@Data
@Builder
@TableName("t_type_dict")
public class TypeDict {
	
	private Long id ;
	
	private String dictType ;
	
	private String dictDesc ;
	
	private int dictOrder ;

}
