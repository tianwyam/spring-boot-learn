package com.tianya.springboot.redis.utils;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import com.tianya.springboot.redis.entity.Goods;
import com.tianya.springboot.redis.thread.PersonShoppingThread;

import redis.clients.jedis.Jedis;

/**
 * @description
 *	redis 中的 list 实现秒杀活动
 * @author TianwYam
 * @date 2021年10月25日下午1:35:30
 */
public class RedisListSecondKill {
	
	public static final String LIST_GOODS = "LIST_GOODS";
	private static final Jedis jedis = new Jedis("localhost", 6379);
	private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);
	
	
	public static void main(String[] args) {
		
		// 模拟生成秒杀商品数据
		System.out.println("开始模拟生成秒杀商品数据......");
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		for (int i = 0; i < 10; i++) {
			Goods goods = Goods.builder()
					.goodsId(i)
					.goodsName(faker.food().fruit())
					.price(faker.number().randomDouble(2, 1, 150))
					.build();
			jedis.lpush(LIST_GOODS, JSON.toJSONString(goods));
		}
		System.out.println("完成模拟生成秒杀商品数据");
		
		// 查询 队列内商品数量
		List<String> lrangeGoods = jedis.lrange(LIST_GOODS, 0, -1);
		if (lrangeGoods == null || lrangeGoods.size() == 0) {
			System.out.println("没有秒杀商品了 或者 秒杀活动已结束");
			return ;
		}
		
		System.out.println("\n所有的秒杀商品：");
		System.out.println(JSON.toJSONString(lrangeGoods, true));
		
		System.out.println("\n所有的用户开始进行抢购：");
		for (int i = 0; i < 30; i++) {
			THREAD_POOL.submit(new PersonShoppingThread(i, jedis));
		}
		
		try {
			THREAD_POOL.awaitTermination(10, TimeUnit.SECONDS);
			THREAD_POOL.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("秒杀活动结束");
	}
	
	

}
