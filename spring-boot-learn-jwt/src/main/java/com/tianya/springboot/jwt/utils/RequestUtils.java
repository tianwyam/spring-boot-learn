package com.tianya.springboot.jwt.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	
	/**
	 * @description
	 *	获取 请求体内 流
	 * @author TianwYam
	 * @date 2021年10月19日上午11:56:52
	 * @param request
	 * @return
	 */
	public static byte[] getBodyStream(HttpServletRequest request) {
		
		try {
			return IOUtils.toByteArray(request.getInputStream());
		} catch (IOException e) {
			log.error("获取请求体内流数据发生异常", e);
		}
		
		return new byte[0];
	}
	

}
