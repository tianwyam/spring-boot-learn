package com.tianya.springboot.freemarker.code.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tianya.springboot.freemarker.code.entity.JavaBeanAttribute;
import com.tianya.springboot.freemarker.code.entity.JavaBeanGenerate;
import com.tianya.springboot.freemarker.utils.FreeMarkerUtils;

/**
 * @Description: 
 * 自动生成代码 工具类
 * @author: TianwYam
 * @date 2021年5月5日 上午9:11:42
 */
public class AutoGenerateCodeUtils {

	/**
	 * @Description: 
	 * 生成JavaBean代码
	 * @author: TianwYam
	 * @date 2021年5月5日 上午9:53:27
	 */
	public static void generateCode() {
		
		//定义属性
		List<JavaBeanAttribute> attributes = new ArrayList<>();
		attributes.add(JavaBeanAttribute.builder().attrName("id").attrType("int").attrComment("主键").build());
		attributes.add(JavaBeanAttribute.builder().attrName("name").attrType("String").attrComment("教师姓名").build());
		attributes.add(JavaBeanAttribute.builder().attrName("sex").attrType("int").attrComment("性别").build());
		attributes.add(JavaBeanAttribute.builder().attrName("birthDate").attrType("Date").attrComment("出生日期").build());
		
		// Java bean 生成 teacher 老师
		JavaBeanGenerate generate = JavaBeanGenerate.builder()
				.className("Teacher")
				.packageName("com.tianya.springboot.freemarker.code")
				.attributes(attributes)
				.importClassList(Arrays.asList("java.util.Date"))
				.path("D:\\javaWork\\spring-boot-learn\\spring-boot-learn-freemarker\\src\\main\\java\\com\\tianya\\springboot\\freemarker\\code\\Teacher.java")
				.build();
		
		// 生成Java bean
		FreeMarkerUtils.fillTemplate(generate.getPath(), "javaBeanGenerate.ftl", generate);
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		generateCode();
		
	}
	
	
	
	
	
	
	
	
}
