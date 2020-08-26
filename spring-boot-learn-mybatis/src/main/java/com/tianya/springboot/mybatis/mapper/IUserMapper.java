package com.tianya.springboot.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tianya.springboot.mybatis.entity.UserBean;

@Mapper
public interface IUserMapper {
	
	
	public List<UserBean> getUserList() ;

}
