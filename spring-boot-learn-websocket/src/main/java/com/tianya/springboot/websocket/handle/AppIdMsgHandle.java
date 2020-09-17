package com.tianya.springboot.websocket.handle;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

/**
 * @description
 *	应用消息处理
 * @author TianwYam
 * @date 2020年9月17日下午4:07:03
 */
@Service
public interface AppIdMsgHandle {

	/**
	 * @description
	 *	消息处理机制
	 * @author TianwYam
	 * @date 2020年9月17日下午4:45:36
	 * @param message
	 * @param session
	 */
	void handleMsg(String message, Session session);
	
	/**
	 * @description
	 *	获取应用ID
	 * @author TianwYam
	 * @date 2020年9月17日下午4:45:48
	 * @return
	 */
	String getAppId() ;
}
