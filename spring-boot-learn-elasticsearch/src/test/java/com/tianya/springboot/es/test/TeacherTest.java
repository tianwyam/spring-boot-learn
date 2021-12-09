package com.tianya.springboot.es.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.es.dao.TeacherEsDao;
import com.tianya.springboot.es.entity.Teacher;

@SpringBootTest
public class TeacherTest {
	
	@Autowired
	private TeacherEsDao teacherEsDao;
	
	@Test
	public void testInsert() {
		
		Teacher teacher = Teacher.builder()
				.id(120L)
				.name("吴彦祖T")
				.age(25)
				.addr("四川成都吃火锅")
				.build();
		teacherEsDao.save(teacher);
	}
	
	
	@Test
	public void getAll() {
		
		Iterable<Teacher> teachers = teacherEsDao.findAll();
		System.out.println(JSON.toJSONString(teachers, true));
	}
	
	
	
	
	

}
