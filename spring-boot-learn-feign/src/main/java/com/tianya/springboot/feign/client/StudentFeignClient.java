package com.tianya.springboot.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tianya.springboot.common.entity.ResultUtis;

@FeignClient(name = "studentFeignClient", url = "${student.feign.url}")
public interface StudentFeignClient {

	@GetMapping("/list")
	public ResultUtis getList() ;
}
