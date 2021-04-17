package com.tianya.springboot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description
 *	控制器 处理请求
 * @author TianwYam
 * @date 2021年4月17日下午1:47:02
 */
@Controller
public class IndexController {
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}

}
