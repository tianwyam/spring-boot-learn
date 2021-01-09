package com.tianya.springboot.validation.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.validation.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {
	
	private MockMvc mockMvc ;
	
	@Autowired
	private WebApplicationContext webApplicationContext ;
	
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	
	@Test
	public void testGetStudent() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/student/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
	}
	
	
	@Test
	@SuppressWarnings("all")
	public void testPostStudent() throws Exception {
		
		Student student = Student.builder().name("tom").email("123@qq.com").id(100).build();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/student/")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(student)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
	}
	
	
	

}
