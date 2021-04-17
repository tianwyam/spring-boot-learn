package com.tianya.springboot.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @description
 * 自定义配置 security 权限等操作
 * @author TianwYam
 * @date 2021年4月17日下午3:03:47
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	/**
	 * 自定义配置权限 等操作
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 不能够重复配置，应该去掉
		//super.configure(http);
		
		http.authorizeRequests()
		.antMatchers("/test").permitAll()
		.antMatchers("/*").authenticated()
		.and()
		.formLogin();
	}
}
