package com.tianya.springboot.jwt.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {
	
	public static HttpServletRequest getRequest() {
		
		ServletRequestAttributes attributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			return attributes.getRequest();
		}
		
		return null ;
	}
	
	
	public static String getHeader(String name) {
		
		
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getHeader(name);
		}
		return null ;
	}
	
	
	
	

}
