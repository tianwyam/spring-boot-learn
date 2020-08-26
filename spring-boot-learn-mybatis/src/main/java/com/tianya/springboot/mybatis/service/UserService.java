package com.tianya.springboot.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianya.springboot.mybatis.entity.UserBean;
import com.tianya.springboot.mybatis.mapper.IUserMapper;

@Service
public class UserService {
	
	@Autowired
	private IUserMapper userMapper ;
	
	public List<UserBean> getList(){
		return userMapper.getUserList();
	}

}
