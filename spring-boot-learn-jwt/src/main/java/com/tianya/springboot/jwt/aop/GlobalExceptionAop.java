package com.tianya.springboot.jwt.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tianya.springboot.jwt.common.ResponseResult;



/**
 * @description
 *	全局异常处理
 * @author TianwYam
 * @date 2021年5月20日下午1:35:22
 */
@RestControllerAdvice
public class GlobalExceptionAop {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseResult exception(Exception e) {
		return ResponseResult.error("请求异常: " + e.getMessage());
	}
	

}
