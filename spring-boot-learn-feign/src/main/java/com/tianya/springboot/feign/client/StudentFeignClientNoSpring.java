package com.tianya.springboot.feign.client;

import com.tianya.springboot.common.entity.ResultUtis;

import feign.RequestLine;


/**
 * @description
 *	不在spring环境下的  feign 接口定义
 * @author TianwYam
 * @date 2020年12月25日下午6:25:42
 */
public interface StudentFeignClientNoSpring {
	

	// 不使用spring环境下的，只能使用 feign自带的 RequestLine 来声明 方法 请求 类型
	@RequestLine("GET /list")
	public ResultUtis getList() ;

}
