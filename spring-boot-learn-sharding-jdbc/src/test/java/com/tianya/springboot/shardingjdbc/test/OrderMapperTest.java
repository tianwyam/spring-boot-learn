package com.tianya.springboot.shardingjdbc.test;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.javafaker.Faker;
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
	
	
	/**
	 * @description
	 *	随机插入
	 * @author TianwYam
	 * @date 2021年10月10日下午3:59:37
	 */
	@Test
	public void insertOrder() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		long currentTimeMillis = System.currentTimeMillis();
		Random random = new Random();
		for (int i = 1; i <= 10; i++) {
			
			int nextInt = random.nextInt(500000000);
			Order order = Order.builder()
					.orderName("订单应用名称：" + faker.app().name())
					.orderType("网购订单")
					.orderDesc("买了款应用：" + faker.app().name() + " 的VIP会员")
					.createUserName(faker.name().fullName())
					.createUserId(i)
					.createTime(new Date(currentTimeMillis + nextInt))
					.build();
			orderMapper.insert(order);
		}
		
	}
	
	
	/**
	 * @description
	 *	查询所有
	 * @author TianwYam
	 * @date 2021年10月10日下午4:01:18
	 */
	@Test
	public void getList() {
		
		// 查询所有 | 时间倒序排序
		List<Order> list = orderMapper.selectList(Wrappers.lambdaQuery(Order.class)
				.orderByDesc(Order::getCreateTime));
		System.out.println("查询所有，总共：" + list.size());
		System.out.println(JSON.toJSONString(list, true));
		
		// 条件查询
		LambdaQueryWrapper<Order> queryWrapper = Wrappers.lambdaQuery(Order.class)
				.like(Order::getOrderDesc, "va")
				.orderByDesc(Order::getOrderDesc);
		List<Order> queryList = orderMapper.selectList(queryWrapper);
		System.out.println("条件查询[order_desc like va]，总共：" + queryList.size());
		System.out.println(JSON.toJSONString(queryList, true));
		
		// 分页查询
		Page<Order> page = new Page<Order>(1, 3);
		// 需要配置分页插件才生效
		Page<Order> pageQuery = orderMapper.selectPage(page, queryWrapper);
		System.out.println("分页查询，总共：" + pageQuery.getTotal() );
		System.out.println(JSON.toJSONString(pageQuery, true));
	}
	
	
	
	
	

}
