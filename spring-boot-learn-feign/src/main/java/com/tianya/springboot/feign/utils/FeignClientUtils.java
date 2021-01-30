package com.tianya.springboot.feign.utils;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * @description
 *	Feign工具类
 * @author TianwYam
 * @date 2021年1月30日上午11:24:21
 */
public class FeignClientUtils {

	/**
	 * @description
	 *	获取 feign客户端
	 * @author TianwYam
	 * @date 2021年1月30日上午11:23:34
	 * @param <T> 客户端类
	 * @param url 调用客户端请求的URL
	 * @param clazz 客户端 class
	 * @return
	 */
	public static <T> T getClient(String url, Class<T> clazz) {

		return Feign.builder()
				.decoder(new GsonDecoder())
				.encoder(new GsonEncoder())
				.target(clazz, url);
	}

}
