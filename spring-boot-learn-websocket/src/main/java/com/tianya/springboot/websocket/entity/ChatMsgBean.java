package com.tianya.springboot.websocket.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMsgBean {
	
	// 消息ID
	private long msgId ;
	
	// 发送者
	private String author ;
	
	// 消息内容
	private String msgContent ;
	
	// 发送时间
	private Date sendTime ;

}
