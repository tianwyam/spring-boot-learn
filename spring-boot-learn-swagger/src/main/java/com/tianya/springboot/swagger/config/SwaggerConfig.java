package com.tianya.springboot.swagger.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.github.xiaoymin.swaggerbootstrapui.filter.SecurityBasicAuthFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig implements WebMvcConfigurer{

	
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.tianya.springboot"))
				.paths(PathSelectors.any())
				.build();
	}
	
	
	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Swagger API文档")
				.description("tianwyam 学习swagger API文档")
				.contact(new Contact("TianwYam", "", "1129064096@qq.com"))
				.version("1.0.0")
				.build();
	}
	
	
	/**
	 * 添加静态资源 位置，防止被 安全框架拦截掉
	 * MVC资源映射，防止 Spring Security | shiro 拦截掉
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		// 添加静态资源 位置，防止被 安全框架拦截掉
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	
	
	
	
	
	
	/*********************************************/
	/******************Swagger简单认证**************/
	/******************防止其他人员进行查看************/
	/*********************************************/
	
	@Value("${swagger.auth.enable:false}")
	private String enableBasicAuth ;
	
	@Value("${swagger.auth.userName:admin}")
	private String userName ;
	
	@Value("${swagger.auth.password:admin}")
	private String password ;
	
	@Bean
	public FilterRegistrationBean<Filter> swaggerSecurityAuthFilter(){
		
		FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<Filter>();
		filter.setFilter(new SecurityBasicAuthFilter());
		
		Map<String, String> initParameters = new HashMap<>();
		initParameters.put("enableBasicAuth", enableBasicAuth);
		initParameters.put("userName", userName);
		initParameters.put("password", password);
		
		filter.setInitParameters(initParameters);
		return filter;
	}
	
}
