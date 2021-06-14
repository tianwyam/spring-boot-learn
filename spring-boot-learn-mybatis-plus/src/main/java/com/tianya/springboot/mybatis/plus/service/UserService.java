package com.tianya.springboot.mybatis.plus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianya.springboot.mybatis.plus.entity.UserBean;
import com.tianya.springboot.mybatis.plus.mapper.IUserMapper;

@Service
public class UserService {
	
	@Autowired
	private IUserMapper userMapper ;
	
	public List<UserBean> getUsers(){
		return userMapper.selectList(null);
	}
	

}
