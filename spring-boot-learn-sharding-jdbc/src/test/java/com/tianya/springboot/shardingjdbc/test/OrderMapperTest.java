package com.tianya.springboot.shardingjdbc.test;

import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tianya.springboot.shardingjdbc.entity.Order;
import com.tianya.springboot.shardingjdbc.mapper.IOrderMapper;

/**
 * @description
 *	测试类
 * @author TianwYam
 * @date 2021年10月9日下午9:10:08
 */
@SpringBootTest
public class OrderMapperTest {
	
	@Autowired
	private IOrderMapper orderMapper ;
	
	
	@Test
	public void insertOrder() {
		
		long currentTimeMillis = System.currentTimeMillis();
		Random random = new Random();
		for (int i = 1; i <= 10; i++) {
			
			int nextInt = random.nextInt(5000000);
			Order order = Order.builder()
					.orderName("手机订单")
					.orderType("网购订单")
					.orderDesc("买了一款手机 红米+" + i)
					.createUserName("张三 + " + i)
					.createUserId(i)
					.createTime(new Date(currentTimeMillis + nextInt))
					.build();
			orderMapper.insert(order);
		}
		
	}
	
	

}
