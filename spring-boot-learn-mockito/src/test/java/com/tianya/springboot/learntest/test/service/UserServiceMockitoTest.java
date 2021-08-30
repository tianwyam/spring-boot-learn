package com.tianya.springboot.learntest.test.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tianya.springboot.learntest.dao.UserRepository;
import com.tianya.springboot.learntest.entity.UserBean;
import com.tianya.springboot.learntest.service.UserService;


/**
 * @description
 *	第二种方式：采用框架powermock进行测试（不依赖数据库、网络等环境）
 *	这种方法不跑真是数据库环境等（在没有真是环境，或者环境比较麻烦的情况）
 *	可以设置些模拟情况进行执行业务方法，看预期结果是否与实际跑出来的情况一致
 * @author TianwYam
 * @date 2021年8月30日下午9:05:13
 */
@RunWith(PowerMockRunner.class)
public class UserServiceMockitoTest {
	
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository; 
	
	@Test
	public void testGetAll() {
		
		// 设置模拟 条件 当执行....返回....
		when(userRepository.findAll()).thenReturn(new ArrayList<>());
		
		// 执行真实方法 进行模拟
		List<UserBean> users = userService.getUsers();
		
		// 验证 被模拟的对象方法执行了几次
		verify(userRepository, times(1)).findAll();
		
		// 判断 结果预期情况
		assertTrue(users.size() == 0);
		
	}

}
