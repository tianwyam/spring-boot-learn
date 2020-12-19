package com.tianya.springboot.swagger.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.github.xiaoymin.swaggerbootstrapui.filter.SecurityBasicAuthFilter;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig implements WebMvcConfigurer{
	
	
	// 扫描包
	@Value("${swagger.basePackage:}")
	private String basePackage ;

	// 注入忽略包的 数组
	@Value("${swagger.ignorePackages:}")
	private List<String> ignorePackages ;
	
	
	/**
	 * @description
	 *	设置整个swagger信息
	 * @author TianwYam
	 * @date 2020年12月19日下午2:50:09
	 * @return
	 */
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				// 文档信息
				.apiInfo(apiInfo())
				.select()
				// 设置 需要 生成API文档 包路径
				// swagger会扫描整个包下的controller，包括JAR里面的
				// 但是swagger不能够排除过滤些不需要显示API文档的controller，只能够自定义
//				.apis(RequestHandlerSelectors.basePackage("com.tianya.springboot"))
				// 自定义扫描包路径，及 需要排除的 过滤掉不需要显示的 路径
				.apis(apis(basePackage, ignorePackages))
				.paths(PathSelectors.any())
				.build();
	}
	
	
	/**
	 * @description
	 *	设置API文档信息
	 * @author TianwYam
	 * @date 2020年12月19日下午2:49:51
	 * @return
	 */
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
	
	
	
	/**
	 * @description
	 *	自定义 扫描包
	 * @author TianwYam
	 * @date 2020年12月19日下午3:22:49
	 * @param basePackage 自定义 扫描的 包路径
	 * @param ignorePackages 自定义 需要忽略的 包路径
	 * @return
	 */
	@SuppressWarnings("all")
	public static Predicate<RequestHandler> apis(String basePackage, List<String> ignorePackages){
		return input->{
			return Optional.fromNullable(input.declaringClass())
					.transform(handlerPackage(basePackage, ignorePackages))
					.or(true);
		};
	}
	
	/**
	 * @description
	 *	处理 扫描类 包
	 * @author TianwYam
	 * @date 2020年12月19日下午3:22:13
	 * @param basePackage 指定要扫描的包路径
	 * @param ignorePackages 指定要忽略的包路径
	 * @return
	 */
	public static Function<Class<?>, Boolean> handlerPackage(final String basePackage, List<String> ignorePackages) {
	    return  input -> {
	    	String classPath = ClassUtils.getPackageName(input);
	    	// 当前扫描的包 不能在 要忽略的包内，才能放行显示 文档
	        return classPath.startsWith(basePackage) 
	        		&& !existIgnorePackages(classPath, ignorePackages);
	    };
	}
	
	
	/**
	 * @description
	 *	判断当前包是否存在于 将要忽略的包中
	 * @author TianwYam
	 * @date 2020年12月19日下午3:19:56
	 * @param classPath 当前扫描到的 类全路径
	 * @param ignorePackages 需要被 忽略的 包
	 * @return
	 */
	public static boolean existIgnorePackages(String classPath, List<String> ignorePackages) {
		if (ignorePackages != null && ignorePackages.size() > 0) {
			for (String ignorePackage : ignorePackages) {
				if (!StringUtils.isEmpty(ignorePackage) 
						&& !StringUtils.isEmpty(classPath) 
						&& classPath.startsWith(ignorePackage)) {
					return true ;
				}
			}
		}
		return false ;
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
