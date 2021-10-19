package com.tianya.springboot.mybatis.plus.test;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;

public class JavaFakerTest {
	

	@Test
	public void testFaker() {
		
		Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
		
		System.out.println(faker.funnyName().name());
		System.out.println(faker.name().username());
		System.out.println(faker.name().fullName());
		
		System.out.println(faker.idNumber().invalid());
		System.out.println(faker.idNumber().invalidSvSeSsn());
		System.out.println(faker.idNumber().ssnValid());
		System.out.println(faker.idNumber().valid());
		System.out.println(new Faker().idNumber().validSvSeSsn());
		
		
		Book book = faker.book();
		System.out.println(book.author());
		System.out.println(book.genre());
		System.out.println(book.publisher());
		System.out.println(book.title());
		
		
	}

}
