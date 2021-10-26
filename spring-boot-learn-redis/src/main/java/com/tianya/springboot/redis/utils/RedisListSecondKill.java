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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description
 *	redis 中的 list 实现秒杀活动
 * @author TianwYam
 * @date 2021年10月25日下午1:35:30
 */
public class RedisListSecondKill {
	
	public static final String LIST_GOODS = "LIST_GOODS";
	
	// redis 客户端连接
//	private static final Jedis jedis = new Jedis("localhost", 6379);
	
	// redis 连接池
	private static final JedisPool JEDIS_POOL = getJedisPool();
	
	// 线程池
	private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(20);
	
	
	/**
	 * @description
	 *	redis连接池配置 
	 * @author TianwYam
	 * @date 2021年10月26日下午8:18:08
	 * @return
	 */
	public static JedisPool getJedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(40);
		poolConfig.setMinIdle(10);
		poolConfig.setMaxTotal(50);
		
		return new JedisPool(poolConfig);
	}
	
	/**
	 * @description
	 *	模拟生成秒杀商品数据
	 * @author TianwYam
	 * @date 2021年10月26日下午8:29:27
	 */
	public static void pushGoods() {
		
		Jedis jedis = JEDIS_POOL.getResource();
		
		// 模拟生成秒杀商品数据
		System.out.println("开始模拟生成秒杀商品数据......");
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		for (int i = 0; i <= 10; i++) {
			Goods goods = Goods.builder()
					.goodsId(i)
					.goodsName(faker.food().fruit())
					.price(faker.number().randomDouble(2, 1, 150))
					.build();
			
			// 往队列中添加 秒杀商品
			jedis.lpush(LIST_GOODS, JSON.toJSONString(goods));
		}
		
		System.out.println("完成模拟生成秒杀商品数据");
		
		jedis.close();
	}
	
	/**
	 * @description
	 *	获取所有的秒杀商品
	 * @author TianwYam
	 * @date 2021年10月26日下午8:28:55
	 * @return
	 */
	public static List<String> getGoodsList() {
		Jedis jedis = JEDIS_POOL.getResource();
		List<String> lrangeGoods = jedis.lrange(LIST_GOODS, 0, -1);
		jedis.close();
		return lrangeGoods;
	}
	
	
	/**
	 * @description
	 *	启动秒杀活动主类
	 * @author TianwYam
	 * @date 2021年10月26日下午8:29:42
	 */
	public static void main(String[] args) {
		
		// 往队列中添加 秒杀商品
		pushGoods();
		
		// 查询 队列内商品数量
		List<String> lrangeGoods = getGoodsList();
		if (lrangeGoods == null || lrangeGoods.size() == 0) {
			System.out.println("没有秒杀商品了 或者 秒杀活动已结束");
			return ;
		}
		
		System.out.println("\n所有的秒杀商品：");
		System.out.println(JSON.toJSONString(lrangeGoods, true));
		
		System.out.println("\n所有的用户开始进行抢购：");
		for (int i = 0; i < 30; i++) {
			THREAD_POOL.submit(new PersonShoppingThread(i, JEDIS_POOL.getResource()));
		}
		
		try {
			THREAD_POOL.awaitTermination(10, TimeUnit.SECONDS);
			THREAD_POOL.shutdown();
			JEDIS_POOL.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("秒杀活动结束");
	}
	
	

}
