package com.tianya.springboot.redis.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	
	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("localhost", 6379);
		
		System.out.println("qq:");
		String qq = jedis.get("qq");
		System.out.println(qq);
		
		// setnx 分布式锁的实现 
		String key = "Lock-redis" ;
		// setnx key如果存在，则可以设置成功，否则返回 0 
		Long setnx = jedis.setnx(key, "1");
		if (setnx != 0 ) {
			// 设置过期时间  5S
			jedis.expire(key, 5);
			System.out.println(key + ": ");
			System.out.println(jedis.get(key));
			// 再次设置值时，已经存在key，则无法设置，返回 0
			System.out.println(jedis.setnx(key, "1"));
		}
		
		/**
		 *   setnx 实现 分布式锁的实现 
		 *   问题1：jedis.expire(key, 5); 必须设置过期时间，否则死锁
		 *   问题2：setnx expire 和业务 非原子性操作
		 *   问题3：设置过期时间后，业务执行时间超过过期时间后，可能发生误删除锁
		 */
		
		
		jedis.close();
	}

}
