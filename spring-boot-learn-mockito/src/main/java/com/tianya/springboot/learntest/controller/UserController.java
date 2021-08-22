package com.tianya.springboot.learntest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.learntest.entity.UserBean;
import com.tianya.springboot.learntest.service.UserService;

/**
 * @description
 *	学生操作请求接口
 * @author TianwYam
 * @date 2021年8月22日下午5:51:10
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	
	@Autowired
	private UserService userService ;
	
	
	@GetMapping("/list")
	public List<UserBean> getUserList(){
		return userService.getUsers();
	}

}
