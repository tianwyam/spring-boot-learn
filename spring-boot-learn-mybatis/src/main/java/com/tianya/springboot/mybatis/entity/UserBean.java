package com.tianya.springboot.mybatis.entity;

import lombok.Data;

@Data
public class UserBean {
	
	private Integer id ;
	
	private String userName ;
	
	private String password ;
	
	private boolean isAdmin ;

}
