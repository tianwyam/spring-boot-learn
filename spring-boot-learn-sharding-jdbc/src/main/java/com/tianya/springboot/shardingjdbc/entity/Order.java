package com.tianya.springboot.shardingjdbc.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;


/**
 * @description
 *	订单表 模型
 * @author TianwYam
 * @date 2021年10月9日下午9:07:10
 */
@Data
@Builder
@TableName(value = "t_order")
public class Order {
	
	@TableId("order_id")
	private Long orderId ;
	
	private String orderName ; 
	
	private String orderType ; 
	
	private String orderDesc ; 
	
	private int createUserId ;
	
	private String createUserName;
	
	@JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
	private Date createTime ;

}
