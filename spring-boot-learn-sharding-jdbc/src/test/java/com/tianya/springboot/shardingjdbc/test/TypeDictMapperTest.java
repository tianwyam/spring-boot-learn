package com.tianya.springboot.shardingjdbc.test;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import com.tianya.springboot.shardingjdbc.entity.TypeDict;
import com.tianya.springboot.shardingjdbc.mapper.TypeDictMapper;

@SpringBootTest
public class TypeDictMapperTest {
	
	@Autowired
	private TypeDictMapper dictMapper ;
	
	
	@Test
	public void getDictList() {
		
		List<TypeDict> list = dictMapper.selectList(null);
		
		System.out.println(JSON.toJSONString(list, true));
		
	}
	
	
	
	@Test
	public void inserDict() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		for (int i = 0; i < 10; i++) {
			
			TypeDict dict = TypeDict.builder()
					.dictType(faker.book().author())
					.dictDesc(faker.book().title())
					.dictOrder(faker.number().randomDigit())
					.build();
			dictMapper.insert(dict);
		}
		
		
	}
	
	
	@Test
	public void delete() {
		
		dictMapper.delete(null);
		
	}
	
	
	
	@Test
	public void update() {
		
		
		dictMapper.selectById(id);
		dictMapper.updateById(entity);
		
	}
	
	

}
