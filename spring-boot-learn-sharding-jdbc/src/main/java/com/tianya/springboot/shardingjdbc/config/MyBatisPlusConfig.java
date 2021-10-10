package com.tianya.springboot.shardingjdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;


/**
 * @description
 *	mybatis-plus 配置
 * @author TianwYam
 * @date 2021年10月10日下午4:44:42
 */
@Configuration
public class MyBatisPlusConfig {
	
	
	/**
	 * @description
	 *	分页插件
	 * @author TianwYam
	 * @date 2021年10月10日下午4:44:59
	 * @return
	 */
	@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
