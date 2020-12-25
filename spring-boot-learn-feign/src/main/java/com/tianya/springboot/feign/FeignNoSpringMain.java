package com.tianya.springboot.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tianya.springboot.common.entity.ResultUtis;
import com.tianya.springboot.feign.client.StudentFeignClientNoSpring;

import feign.Feign;
import feign.gson.GsonDecoder;

/**
 * @description
 *	在不是 spring环境下 、 springboot环境下的 使用feign
 * @author TianwYam
 * @date 2020年12月25日下午6:17:56
 */
public class FeignNoSpringMain {

	public static void main(String[] args) {
		
		
		StudentFeignClientNoSpring studentFeignClient = Feign.builder()
			.decoder(new GsonDecoder())
			.target(StudentFeignClientNoSpring.class, "http://localhost:8081/api/v1/student");
		
		ResultUtis list = studentFeignClient.getList();
		
		System.out.println(JSON.toJSONString(list, SerializerFeature.PrettyFormat));
	}
	
}
