package com.tianya.springboot.jwt.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {
	
	/**
	 * @description
	 *	从容器中取请求 request
	 * @author TianwYam
	 * @date 2021年5月21日上午10:18:59
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		
		ServletRequestAttributes attributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			return attributes.getRequest();
		}
		
		return null ;
	}
	
	
	/**
	 * @description
	 *	从请求request中取header
	 * @author TianwYam
	 * @date 2021年5月21日上午10:19:24
	 * @param name
	 * @return
	 */
	public static String getHeader(String name) {
		
		
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getHeader(name);
		}
		return null ;
	}
	
	
	
	

}
