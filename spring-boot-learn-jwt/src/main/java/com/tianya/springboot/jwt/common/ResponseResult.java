package com.tianya.springboot.jwt.common;

import lombok.Builder;
import lombok.Data;

/**
 * @description
 *	响应的返回结果体
 * @author TianwYam
 * @date 2021年5月19日下午3:57:13
 */
@Data
@Builder
public class ResponseResult {
	
	public static final int SUCCESS_FLAG = 1 ;
	public static final String SUCCESS_MSG = "成功" ;
	
	public static final int ERROR_FLAG = 0 ;
	public static final String ERROR_MSG = "失败" ;
	
	// 返回标识
	// 0:失败 1:成功
	private int flag ;
	
	// 结果返回描述
	private String message ;
	
	// 返回数据
	private Object data ;
	
	
	
	public static ResponseResult success() {
		return ResponseResult.builder()
				.flag(SUCCESS_FLAG)
				.message(SUCCESS_MSG)
				.build();
	}
	
	public static ResponseResult success(Object data) {
		return ResponseResult.builder()
				.flag(SUCCESS_FLAG)
				.message(SUCCESS_MSG)
				.data(data)
				.build();
	}
	
	
	
	public static ResponseResult error() {
		return ResponseResult.builder()
				.flag(ERROR_FLAG)
				.message(ERROR_MSG)
				.build();
	}
	
	
	public static ResponseResult error(String message) {
		return ResponseResult.builder()
				.flag(ERROR_FLAG)
				.message(message)
				.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
