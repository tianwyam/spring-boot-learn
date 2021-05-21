package com.tianya.springboot.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.jwt.annotation.NotAuth;
import com.tianya.springboot.jwt.common.ResponseResult;

/**
 * @description
 *	模拟请求资源 控制器
 * @author TianwYam
 * @date 2021年5月21日上午10:01:51
 */
@RestController
public class IndexController {
	
	
	// 模拟 首页不需要认证
	@NotAuth
	@GetMapping({"/index","/",""})
	public ResponseResult index() {
		return ResponseResult.success("项目首页");
	}
	
	
	/**
	 * @description
	 *	模拟 新增，需要认证
	 * @author TianwYam
	 * @date 2021年5月21日上午10:04:38
	 * @return
	 */
	@PostMapping("/add")
	public ResponseResult add() {
		return ResponseResult.success("新增操作，需要认证");
	}
	
	

}
