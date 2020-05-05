package com.tianya.springboot.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtis {

	private int code ;
	
	private String msg ;
	
	private Object data ;
	
	private PageUtils page ;
	
	
	public static ResultUtis success() {
		return new ResultUtis(ResultCode.SUCCESS);
	}
	
	public static ResultUtis success(Object data) {
		return success(data, null);
	}
	
	public static ResultUtis success(Object data, PageUtils page) {
		return ResultUtis.builder()
				.data(data)
				.code(ResultCode.SUCCESS.code)
				.msg(ResultCode.SUCCESS.msg)
				.page(page)
				.build();
	}
	
	public static ResultUtis error(ResultCode resultCode) {
		return new ResultUtis(resultCode);
	}
	
	public static ResultUtis error(int code, String msg) {
		return ResultUtis.builder()
				.code(code)
				.msg(msg)
				.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ResultUtis(ResultCode resultCode) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
	}
	
}
