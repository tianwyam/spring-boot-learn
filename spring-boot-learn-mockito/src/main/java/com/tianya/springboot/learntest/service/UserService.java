package com.tianya.springboot.learntest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianya.springboot.learntest.dao.UserRepository;
import com.tianya.springboot.learntest.entity.UserBean;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository; 
	
	/**
	 * @description
	 *	实际调用数据库的服务
	 * @author TianwYam
	 * @date 2021年8月22日下午6:18:44
	 * @return
	 */
	public List<UserBean> getUsers(){
		return userRepository.findAll() ;
	}
	

}
