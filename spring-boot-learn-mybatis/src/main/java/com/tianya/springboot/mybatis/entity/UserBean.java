package com.tianya.springboot.mybatis.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBean {
	
	private Integer id ;
	
	private String userName ;
	
	private String password ;
	
	private boolean isAdmin ;

}
