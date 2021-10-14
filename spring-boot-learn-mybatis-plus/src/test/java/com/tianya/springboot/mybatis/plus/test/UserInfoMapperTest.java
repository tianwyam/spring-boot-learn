package com.tianya.springboot.mybatis.plus.test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.javafaker.Faker;
import com.tianya.springboot.mybatis.plus.entity.UserInfo;
import com.tianya.springboot.mybatis.plus.mapper.IUserInfoMapper;

/**
 * @description
 *	简单测试
 * @author TianwYam
 * @date 2021年10月13日下午4:50:31
 */
@SpringBootTest
public class UserInfoMapperTest {
	
	@Autowired
	private IUserInfoMapper userInfoMapper ;
	
	// 性别字典
	private static final Map<Integer, String> SEX_MAP = new HashMap<Integer, String>(){
		private static final long serialVersionUID = -218680922970957467L;
	{
		put(0, "女");
		put(1, "男");
		put(2, "未知");
	}};
	
	
	@Test
	public void getList() {
		// 查询所有
		List<UserInfo> selectList = userInfoMapper.selectList(null);
		System.out.println("所有的用户基本信息：");
		System.out.println(JSON.toJSONString(selectList, true));
	}
	
	
	
	@Test
	public void insert() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		// 测试 新增 10 个用户
		for (int i = 0; i < 10; i++) {
			
			// 用户信息
			UserInfo userInfo = UserInfo.builder()
					.userName(faker.name().fullName())
					.userIdcard(faker.idNumber().invalid())
					.homeAddress(faker.address().fullAddress())
					.birthDay(faker.date().birthday())
					.age(faker.number().numberBetween(1, 120))
					.height(faker.number().numberBetween(50, 200))
					.sex(SEX_MAP.get(faker.number().numberBetween(0, 2)))
					.build();
			
			// 新增
			userInfoMapper.insert(userInfo);
		}
		
		getList();
	}

	
	@Test
	public void delete() {
		
		// 删除
		userInfoMapper.deleteById(20);
		
	}
	
	
	
	@Test
	public void update() {
		
		// 查询单个
		UserInfo userInfo = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
				.eq(UserInfo::getUserId, 27));
		
		System.out.println("修改前：");
		System.out.println(JSON.toJSONString(userInfo, true));
		
		// 修改值
		userInfo.setAge(70);
		userInfo.setSex(SEX_MAP.get(1));
		userInfo.setHeight(178);
		
		// 更新
		userInfoMapper.updateById(userInfo);
		
		System.out.println("修改后：");
		System.out.println(JSON.toJSONString(userInfo, true));
	}
	
	
	
	
	
	/**
	 * @description
	 *	分页查询
	 * @author TianwYam
	 * @date 2021年10月13日下午9:07:07
	 */
	@Test
	public void getQueryList() {
		
		// 查询条件（查询身高在1米-2米之间的 以年龄倒序排序，查询第一页3个 TOP3）
		LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery(UserInfo.class)
				.orderByDesc(UserInfo::getAge)
				.between(UserInfo::getHeight, 100, 200);
		
		Page<UserInfo> page = new Page<UserInfo>(1, 3);
		page.setSearchCount(true);
		
		// 若是没有配置 分页插件，则 selectPage 分页API 不生效
		page = userInfoMapper.selectPage(page, queryWrapper);
		
		System.out.println("分页查询：");
		System.out.println("总记录数：" + page.getTotal());
		System.out.println(JSON.toJSONString(page, true));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
