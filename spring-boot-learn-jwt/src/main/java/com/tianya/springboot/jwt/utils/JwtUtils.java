package com.tianya.springboot.jwt.utils;

import java.util.Base64;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.tianya.springboot.jwt.entity.TokenReqBean;

/**
 * @description
 *	JWT token生成工具
 * @author TianwYam
 * @date 2021年5月19日下午5:31:37
 */
public class JwtUtils {
	
	// 私钥
	public static String secret = "SECRET#PWD#234$345%456" ;
	
	// 失效时间  2 小时
	public static long EXPIRES_TIME = 1000 * 60 * 60 * 2 ;
	
	
	/**
	 * @description
	 *	生成TOKEN
	 * @author TianwYam
	 * @date 2021年5月19日下午5:43:36
	 * @param tokenReqBean
	 * @return
	 */
	public static String token(TokenReqBean tokenReqBean) {
		
		long nowTime = System.currentTimeMillis();
		
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.create()
				.withSubject(encode(JSON.toJSONString(tokenReqBean)))
				.withIssuedAt(new Date(nowTime))
				.withExpiresAt(new Date(nowTime + EXPIRES_TIME))
				.sign(algorithm);
	}
	
	
	
	/**
	 * @description
	 *	验证TOKEN是否过期等等
	 * @author TianwYam
	 * @date 2021年5月19日下午5:51:22
	 * @param token
	 * @return 过期 则抛出异常
	 */
	public static TokenReqBean checkToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		String subject = verifier.verify(token).getSubject();
		return JSON.parseObject(decode(subject), TokenReqBean.class);
	}
	
	
	
	
	
	
	
	
	public static String encode(String content) {
		return Base64.getEncoder()
				.encodeToString(content.getBytes());
	}
	
	
	public static String decode(String content) {
		return new String(Base64.getDecoder()
				.decode(content));
	}
	
	
	
	
	
	
	
	

}
