package com.tianya.springboot.mybatis;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerateMain {
	
	/**
	 * @description
	 *	运行 Mybatis Generate 
	 *		方式一，采用main方法方式运行
	 *		方式二，采用maven运行 mybatis-generator:generate
	 * @author TianwYam
	 * @date 2021年1月9日下午2:01:10
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("开始生成.....");
		List<String> warnings = new ArrayList<>();
		try (
				InputStream inputStream = MybatisGenerateMain.class
					.getClassLoader()
					.getResourceAsStream("generatorConfig.xml"); ){
			ConfigurationParser parser = new ConfigurationParser(warnings);
			Configuration config = parser.parseConfiguration(inputStream);
			DefaultShellCallback defaultShellCallback = new DefaultShellCallback(true);
			MyBatisGenerator generator = new MyBatisGenerator(config, defaultShellCallback, warnings);
			generator.generate(null);
			System.out.println("生成完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
