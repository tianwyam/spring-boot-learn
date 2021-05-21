package com.tianya.springboot.jwt.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springboot.jwt.annotation.NotAuth;
import com.tianya.springboot.jwt.common.ResponseResult;
import com.tianya.springboot.jwt.entity.TokenReqBean;
import com.tianya.springboot.jwt.utils.JwtUtils;

/**
 * @description
 *	控制器
 * @author TianwYam
 * @date 2021年5月21日上午9:56:43
 */
@RestController
public class TokenCcontroller {
	

	/**
	 * @description
	 *	生成TOKEN
	 * @author TianwYam
	 * @date 2021年5月21日上午10:00:51
	 * @param token
	 * @return
	 */
	// 生成TOKEN本身这个接口是不需要认证的
	@NotAuth
	@PostMapping("/token")
	public ResponseResult token(@RequestBody TokenReqBean token) {
		
		if (token == null) {
			return ResponseResult.error("获取token必须传参");
		}
		
		if (StringUtils.isBlank(token.getTenant())) {
			return ResponseResult.error("用户参数不能为空");
		}
		
		if (StringUtils.isBlank(token.getCredentials())) {
			return ResponseResult.error("凭证参数不能为空");
		}
		
		// 判断用户是否存在	
		// 具体项目具体编写
		// 判断成功后，生成token
		return ResponseResult.success(JwtUtils.token(token));
	}

}
