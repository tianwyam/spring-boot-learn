package com.tianya.springboot.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("users")
public class UserBean {
	
	@TableId(type = IdType.AUTO, value = "id")
	private Integer id ;
	
	@TableField("username")
	private String userName ;
	
	@TableField
	private String password ;
	
	@TableField("isadmin")
	private boolean isAdmin ;

}
