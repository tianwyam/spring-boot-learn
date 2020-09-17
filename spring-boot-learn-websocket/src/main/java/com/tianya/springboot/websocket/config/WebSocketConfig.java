package com.tianya.springboot.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
	
	
	/**
	 * @description
	 *	若是使用spring boot内置 web容器 tomcat ，则需要添加此类，否则会报错
	 *	若是使用外置web容器，则可以不用添加
	 * @author TianwYam
	 * @date 2020年9月17日上午11:30:03
	 * @return
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
