package com.tianya.springboot.redis.thread;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.Faker;

import redis.clients.jedis.Jedis;

/**
 * @description
 *	用户抢购线程
 * @author TianwYam
 * @date 2021年10月25日下午1:22:09
 */
public class PersonShoppingThread  implements Runnable{
	
	public static final String LIST_GOODS = "LIST_GOODS";
	
	// 顺序
	private int index ;
	
	// 用户姓名
	private String name ;
	
	private Jedis jedis ;
	
	public PersonShoppingThread(int index, Jedis jedis) {
		this.index = index ;
		this.jedis = jedis ;
		this.name = new Faker(Locale.SIMPLIFIED_CHINESE).name().fullName();
	}

	@Override
	public void run() {
		
		Long len = jedis.llen(LIST_GOODS);
		if (len == null || len <= 0) {
			System.out.println(String.format(" %d : %s 商品已经卖完了，没有抢到了秒杀商品", index, name));
			return ;
		}
		
		// 队列中取出商品
		String goods = jedis.rpop(LIST_GOODS);
		if (StringUtils.isNotBlank(goods)) {
			System.out.println(String.format(" %d : %s 抢到了 秒杀商品：%s" , index, name, goods));
		} else {
			System.out.println(String.format(" %d : %s 没有抢到秒杀商品", index, name));
		}
		
	}

}
