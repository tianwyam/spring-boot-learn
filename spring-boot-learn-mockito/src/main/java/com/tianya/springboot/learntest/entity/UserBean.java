package com.tianya.springboot.learntest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserBean {
	
	@Id
	@Column
	private Integer id ;
	
	@Column
	private String username ;
	
	@Column
	private String password ;
	
	@Column
	private boolean isadmin ;

}
