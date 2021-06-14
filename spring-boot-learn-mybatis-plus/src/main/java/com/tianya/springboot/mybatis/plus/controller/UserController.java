package com.tianya.springboot.mybatis.plus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.mybatis.plus.entity.UserBean;
import com.tianya.springboot.mybatis.plus.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService ;
	
	@GetMapping("/list")
	public List<UserBean> getUsers(){
		return userService.getUsers();
	}

}
