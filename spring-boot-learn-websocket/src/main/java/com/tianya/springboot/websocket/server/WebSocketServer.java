package com.tianya.springboot.websocket.server;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianya.springboot.websocket.handle.AppIdMsgHandle;

import lombok.extern.slf4j.Slf4j;

/**
 * @description
 *	WebSocket 服务端
 * 每种应用下，对应的会话连接
 * <pre>key: appId 不同应用，
 * 如：
 * 	msg对应消息推送，
 * 	log对应日志推送等，
 * 	chat对应聊天信息推送</pre>
 * @author TianwYam
 * @date 2020年9月17日下午1:47:22
 */
@Slf4j
@Service
@ServerEndpoint(value = "/ws/{appId}")
public class WebSocketServer {
	
	// 自动注入消息处理的实现
	// @Autowired 在这儿自动注入会报空指针异常，取不到
	private static Set<AppIdMsgHandle> appIdMsgHandles ;

	/*** 每种应用下，对应的会话连接 */
	private static final ConcurrentHashMap<String, LinkedHashSet<Session>> APPID_CONNECT_SESSION_MAP = new ConcurrentHashMap<>();
	
	
	/**
	 * @description
	 *	session 连接 开始
	 * @author TianwYam
	 * @date 2020年9月17日下午2:27:12
	 * @param session 会话连接
	 * @param appId 应用ID
	 */
	@OnOpen
	public void open(Session session, @PathParam(value = "appId") String appId) {
		
		// 获取此应用下所有连接
		LinkedHashSet<Session> sessions = getSessions(appId);
		if (sessions == null) {
			sessions = new LinkedHashSet<>();
			APPID_CONNECT_SESSION_MAP.put(appId, sessions);
		}
		
		// 新会话session 连接成功
		sessions.add(session);
		log.info("新连接进入，目前[{}]总人数：{}", appId, sessions.size());
	}
	
	
	/**
	 * @description
	 *	session 连接关闭
	 * @author TianwYam
	 * @date 2020年9月17日下午3:32:45
	 * @param session
	 * @param appId
	 */
	@OnClose
	public void close(Session session, @PathParam(value = "appId") String appId) {
		
		// 获取此应用下所有连接
		LinkedHashSet<Session> sessions = getSessions(appId);
		if (sessions != null) {
			// 会话session 连接断开
			sessions.remove(session);
			log.info("连接断开，目前[{}]总人数：{}", appId, sessions.size());
		}
		
	}
	
	
	/**
	 * @description
	 *	接受消息 
	 * @author TianwYam
	 * @date 2020年9月17日下午3:39:22
	 * @param message 消息内容
	 * @param session 会话
	 * @param appId 应用
	 */
	@OnMessage
	public void onMessage(String message, Session session, @PathParam(value = "appId") String appId) {
		
		// 消息处理
		for (AppIdMsgHandle appIdMsgHandle : appIdMsgHandles) {
			if (Objects.equals(appIdMsgHandle.getAppId(), appId)) {
				appIdMsgHandle.handleMsg(message, session);
			}
		}
	}
	
	

	/**
	 * @description
	 *	连接 报错
	 * @author TianwYam
	 * @date 2020年9月17日下午3:50:12
	 * @param session
	 * @param error
	 */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket发生错误", error);
    }

	
	
	
	/**
	 * @description
	 *	根据应用获取所有在线人员
	 * @author TianwYam
	 * @date 2020年9月17日下午3:17:04
	 * @param appId 应用ID
	 * @return
	 */
	public static LinkedHashSet<Session> getSessions(String appId){
		return APPID_CONNECT_SESSION_MAP.get(appId);
	}
	
	
	
	/**
	 * @description
	 *	发送文本消息
	 * @author TianwYam
	 * @date 2020年9月17日下午3:52:52
	 * @param session
	 * @param message
	 */
	public static void sendTxtMsg(Session session , String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			log.error("websocket发生消息失败", e);
		}
	}



	/**
	 * @description
	 * <pre>
	 *	WebSocket是线程安全的，有用户连接时就会创建一个新的端点实例，一个端点只能保证一个线程调用。总结就是，WebSocket是多例的。
	 *	Autowired 是程序启动时就注入进去
	 *	用静态变量来保存消息处理实现类，避免出现空指针异常
	 *	另一种方式就是 使用 ApplicationContext.getBean()的方式也可以获取
	 *	</pre>
	 * @author TianwYam
	 * @date 2020年9月17日下午7:43:13
	 * @param appIdMsgHandles
	 */
	@Autowired
	public void setAppIdMsgHandles(Set<AppIdMsgHandle> appIdMsgHandles) {
		WebSocketServer.appIdMsgHandles = appIdMsgHandles;
	}


	
	
	
	
	
}
