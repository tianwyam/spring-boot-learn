package com.tianya.springboot.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tianya.springboot.mybatis.entity.UserBean;

@Mapper
public interface IUserMapper {
	
	
	public List<UserBean> getUserList() ;
	
	public List<Map<String, Object>> getUserTableColumns();

}
