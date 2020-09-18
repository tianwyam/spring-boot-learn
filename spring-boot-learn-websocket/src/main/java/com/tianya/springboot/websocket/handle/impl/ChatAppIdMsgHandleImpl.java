package com.tianya.springboot.websocket.handle.impl;

import java.util.LinkedHashSet;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.tianya.springboot.websocket.handle.AppIdMsgHandle;
import com.tianya.springboot.websocket.server.WebSocketServer;

@Service
public class ChatAppIdMsgHandleImpl implements AppIdMsgHandle{
	
	private static final String APP_ID = "chat" ;

	@Override
	public void handleMsg(String message, Session session) {
		
		LinkedHashSet<Session> sessions = WebSocketServer.getSessions(APP_ID);
		if (sessions != null) {
			for (Session otherSession : sessions) {
				// 排除自己
//				if (!Objects.equals(otherSession, session)) {
					WebSocketServer.sendTxtMsg(otherSession, message);
//				}
			}
		}
		
	}

	@Override
	public String getAppId() {
		return APP_ID;
	}

}
