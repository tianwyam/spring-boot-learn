package com.tianya.springboot.redis.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import com.tianya.springboot.redis.entity.Goods;

@SpringBootTest
public class RedisTest {
	
	private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(20);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate ;
	
	public static final String LIST_GOODS = "LIST_GOODS";
	
	@Test
	public void listPush() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		List<String> goodsList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Goods goods = Goods.builder()
					.goodsId(i)
					.goodsName(faker.food().fruit())
					.price(faker.number().randomDouble(2, 1, 100))
					.build();
			goodsList.add(JSON.toJSONString(goods));
		}
		
		// 存入商品
		stringRedisTemplate.opsForList().leftPushAll(LIST_GOODS, goodsList);
		// 取 商品
		List<String> rangeGoods = stringRedisTemplate.opsForList().range(LIST_GOODS, 0, -1);
		System.out.println(JSON.toJSONString(rangeGoods, true));
	}
	
	
	@Test
	public void listRange() {
		
		List<String> rangeGoods = stringRedisTemplate.opsForList().range(LIST_GOODS, 0, -1);
		System.out.println("所有的秒杀商品：");
		System.out.println(JSON.toJSONString(rangeGoods, true));
	}
	
	
	
	
	@Test
	public void listPop() {
		

		ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
		List<String> rangeGoods = opsForList.range(LIST_GOODS, 0, -1);
		if (rangeGoods == null || rangeGoods.size() == 0) {
			System.out.println("没有秒杀商品了 或者 秒杀活动已结束");
			return ;
		}
		
		System.out.println("所有的秒杀商品：");
		System.out.println(JSON.toJSONString(rangeGoods, true));
		
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		System.out.println("\n所有的用户开始进行抢购：");
		for (int i = 0; i < 20; i++) {
			
			final int x = i ;
			THREAD_POOL.execute(()->{
				
				// 用户
				String person = x + " : " + faker.name().fullName() ;
				
				// 获取长度
//				Long size = stringRedisTemplate.opsForList().size(LIST_GOODS);
//				if (size == null || size <= 0) {
//					System.out.println(person + " 商品已经卖完了，没有抢到了秒杀商品");
//					return ;
//				}
				
				
				String goods = opsForList.leftPop(LIST_GOODS);
				if (StringUtils.isNotBlank(goods)) {
					System.out.println(person + " 抢到了 秒杀商品：" + goods);
				} else {
					System.out.println(person + " 没有抢到秒杀商品");
				}
				
			});
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	

}
