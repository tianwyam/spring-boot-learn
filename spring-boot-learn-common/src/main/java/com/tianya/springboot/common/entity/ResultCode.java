package com.tianya.springboot.common.entity;

public enum ResultCode {
	
	
	SUCCESS(200, "成功"),
	
	ERROR(400, "失败"),
	
	SERVER_ERROR(500, "服务发生异常")
	
	;
	
	
	
	
	
	public int code ;
	
	public String msg ;
	
	
	private ResultCode() {}


	private ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
