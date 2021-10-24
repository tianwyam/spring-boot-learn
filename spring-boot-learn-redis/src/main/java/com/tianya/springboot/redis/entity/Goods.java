package com.tianya.springboot.redis.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description
 *	商品货物
 * @author TianwYam
 * @date 2021年10月24日下午6:33:17
 */
@Data
@Builder
public class Goods {
	
	// 商品编号
	private int goodsId ;
	
	// 商品名称
	private String goodsName ;
	
	// 价格
	private double price ;

}
