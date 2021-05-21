package com.tianya.springboot.jwt.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description
 *	TOKEN认证请求实体
 * @author TianwYam
 * @date 2021年5月19日下午4:20:34
 */
@Data
@Builder
public class TokenReqBean {
	
	
	// 租户（用户）
	private String tenant ;
	
	// 凭证 （密码）
	private String credentials ;
	
	
	// 厂商（系统）
	private String system ;
	
}
