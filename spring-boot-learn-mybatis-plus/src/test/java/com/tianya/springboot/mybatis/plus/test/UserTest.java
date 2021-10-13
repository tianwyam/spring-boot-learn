package com.tianya.springboot.mybatis.plus.test;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.javafaker.Faker;
import com.tianya.springboot.mybatis.plus.entity.UserBean;
import com.tianya.springboot.mybatis.plus.mapper.IUserMapper;

@SpringBootTest
public class UserTest {
	
	@Autowired
	private IUserMapper userMapper ;
	
	
	/**
	 * @description
	 *	获取用户
	 * @author TianwYam
	 * @date 2021年10月12日上午11:43:07
	 */
	@Test
	public void getUsers() {
		
		// 获取所有
		List<UserBean> userList = userMapper.selectList(null);
		
		System.out.println(JSON.toJSONString(userList, true));
		
	}
	
	
	/**
	 * @description
	 *	随机插入用户
	 * @author TianwYam
	 * @date 2021年10月12日上午11:29:10
	 */
	@Test
	public void insert() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		for (int i = 0; i < 5; i++) {
			UserBean user = UserBean.builder()
					.userName(faker.name().fullName())
					.password(faker.crypto().md5())
					.isAdmin(faker.bool().bool())
					.build();
			userMapper.insert(user);
		}
		
		getUsers();
	}
	
	
	
	/**
	 * @description
	 *	删除
	 * @author TianwYam
	 * @date 2021年10月12日上午11:42:43
	 */
	@Test
	public void delete() {
		
		IntStream.range(1, 10).forEach(i->{
			userMapper.deleteById(i);
		});
		
		getUsers();
	}
	
	
	
	/**
	 * @description
	 *	条件查询
	 * @author TianwYam
	 * @date 2021年10月13日下午4:07:05
	 */
	@Test
	public void getUserQueryList() {
		
		// 条件查询 QueryWrapper 方式
		QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("id", 25, 30);
		queryWrapper.eq("isadmin", true);
		List<UserBean> queryList = userMapper.selectList(queryWrapper);
		
		System.out.println(JSON.toJSONString(queryList, true));
		
	}
	
	

}
