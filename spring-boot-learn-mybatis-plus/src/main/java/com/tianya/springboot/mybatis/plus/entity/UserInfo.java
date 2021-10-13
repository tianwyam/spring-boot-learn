package com.tianya.springboot.mybatis.plus.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;


/**
 * @description
 *	用户基本信息
 * @author TianwYam
 * @date 2021年10月13日下午4:24:36
 */
@Data
@Builder
@TableName("user_info")
public class UserInfo {
	
	// 用户ID
	// 标注为表主键ID，若是没有标注，则 updateById会报错
	@TableId
	private Long userId ;
	
	// 用户姓名
	private String userName ;
	
	// 用户身份证
	private String userIdcard ;
	
	// 家庭地址
	private String homeAddress ;
	
	// 年龄
	private int age ;
	
	// 身高CM
	private int height ;
	
	// 性别
	private String sex ;
	
	// 出生日期
	@JSONField(format = "yyyy年MM月dd日")
	private Date birthDay ;

}
