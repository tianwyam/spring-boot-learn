package com.tianya.springboot.mybatis.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.tianya.springboot.mybatis.entity.UserBean;
import com.tianya.springboot.mybatis.mapper.IUserMapper;

@SpringBootTest
public class UserBeanTest {
	
	@Autowired
	private IUserMapper userMapper;
	
	
	@Test
	public void batchInsert() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		List<UserBean> userBeans = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			UserBean user = UserBean.builder()
					.userName(faker.name().fullName())
					.password(faker.idNumber().validSvSeSsn())
					.isAdmin(faker.bool().bool())
					.build();
			userBeans.add(user);
		}
		
		userMapper.batchInsert(userBeans);
	}

}
