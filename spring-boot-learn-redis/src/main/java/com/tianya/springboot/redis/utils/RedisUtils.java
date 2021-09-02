package com.tianya.springboot.redis.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.redis.entity.StudentBean;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	
	private static final Jedis jedis = new Jedis("localhost", 6379);
	
	public static final String KEY_ZSET_RANK_STUD = "KEY_ZSET_RANK_STUD" ;
	public static final String KEY_MAP_STUD = "KEY_MAP_STUD" ;
	
	public static void main(String[] args) {
		
		zset();
		
		map();
		
		
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
	
	
	/**
	 * @description
	 *	使用 map 缓存 对象
	 * @author TianwYam
	 * @date 2021年9月2日下午7:41:21
	 */
	public static void map() {
		
		Map<String, String> studMap = new HashMap<>();
		studMap.put("id", "12");
		studMap.put("name", "金城武");
		studMap.put("score", "400");
		
//		jedis.hdel(KEY_MAP_STUD);
//		jedis.hset(KEY_MAP_STUD, studMap);
		
		jedis.hset(KEY_MAP_STUD, "id", "12");
		jedis.hset(KEY_MAP_STUD, "name", "金城武");
		jedis.hset(KEY_MAP_STUD, "score", "400");
		
		
		System.out.println("\n使用map数据结果操作对象：");
		Map<String, String> hgetAll = jedis.hgetAll(KEY_MAP_STUD);
		System.out.println(JSON.toJSONString(hgetAll));
		
		
	}
	
	
	/**
	 * @description
	 *	使用 zset 进行排序
	 * @author TianwYam
	 * @date 2021年9月2日下午7:36:44
	 */
	public static void zset() {
		
		// 成绩添加到 redis缓存中
		List<StudentBean> students = initData();
		System.out.println("按照成绩排序前的学生：");
		System.out.println(JSON.toJSONString(students));
		for (StudentBean stu : students) {
			jedis.zadd(KEY_ZSET_RANK_STUD, stu.getScore(), stu.getName());
		}
		
		// 成绩排序
		Set<String> zrange = jedis.zrange(KEY_ZSET_RANK_STUD, 0, -1);
		System.out.println("\n按照成绩排序后的学生：");
		System.out.println(JSON.toJSONString(zrange));
		
		// 逆序输出  -1 代表输出所有的
		Set<String> zrevrange = jedis.zrevrange(KEY_ZSET_RANK_STUD, 0, -1);
		System.out.println("\n按照成绩逆序排序后的学生：");
		System.out.println(JSON.toJSONString(zrevrange));
		
	}
	
	
	public static List<StudentBean> initData() {
		
		List<StudentBean> studentBeans = new ArrayList<>();
		studentBeans.add(StudentBean.builder().id(1).score(120).name("张三丰").build());
		studentBeans.add(StudentBean.builder().id(2).score(212).name("李寻欢").build());
		studentBeans.add(StudentBean.builder().id(3).score(125).name("渡劫").build());
		studentBeans.add(StudentBean.builder().id(4).score(325).name("心结").build());
		studentBeans.add(StudentBean.builder().id(5).score(125).name("毒男").build());
		
		return studentBeans ;
	}

}
