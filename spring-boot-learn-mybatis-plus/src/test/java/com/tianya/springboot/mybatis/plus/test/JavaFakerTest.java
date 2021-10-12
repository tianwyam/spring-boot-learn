package com.tianya.springboot.mybatis.plus.test;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class JavaFakerTest {
	

	@Test
	public void testFaker() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		System.out.println(faker.funnyName().name());
		System.out.println(faker.name().username());
		System.out.println(faker.name().fullName());
		
	}

}
