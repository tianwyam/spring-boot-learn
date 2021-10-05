package com.tianya.springboot.mybatis.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description
 *	表 元数据 字段列信息
 * @author TianwYam
 * @date 2021年10月5日下午8:08:08
 */
@Data
@Builder
public class TableColumnBean {
	
	private String dbName ;
	
	private String tableName ;
	
	private String columnName ;
	
	private String autoIncrement ;
	
	private String generatedColumn  ;
	
	private String columnType ;
	
	private int columnLength ;
	
	private String columnRemark ;
	
	
	

}
