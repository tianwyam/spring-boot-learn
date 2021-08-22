package com.tianya.springboot.learntest.test.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.learntest.entity.UserBean;
import com.tianya.springboot.learntest.service.UserService;

/**
 * @description
 *	第一种方式：spring boot 测试方式
 *	它可以模拟springboot启动整个容器
 * @author TianwYam
 * @date 2021年8月22日下午6:19:44
 */
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testGetAll() {
		
		List<UserBean> users = userService.getUsers();
		
		System.out.println(JSON.toJSONString(users, true));
		
	}

}
