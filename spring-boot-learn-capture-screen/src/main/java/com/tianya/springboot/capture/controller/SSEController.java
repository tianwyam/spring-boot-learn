package com.tianya.springboot.capture.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.tianya.springboot.capture.utils.CaptureScreenUtils;

@RestController
@RequestMapping("/sse")
public class SSEController {

	// 服务端推送 返回结果必须以 data: 开始
	public static final String SSE_RETURN_START = "data:" ;
	
	// // 服务端推送 返回结果必须以 \n\n 结束
	public static final String SSE_RETURN_END = "\n\n" ;
	
	// 服务端推送SSE技术 内容格式必须是  text/event-stream
	@GetMapping(value = {"","/"}, produces = "text/event-stream;charset=UTF-8")
	public String index() {
		String capture = CaptureScreenUtils.capture();
		// 返回的内容 必须以 data: 开头，以 \n\n 结尾，不然前端JS处始终不会进入到 onmessage 方法内
		return SSE_RETURN_START + capture + SSE_RETURN_END ;
	}
	
	
	
	
	
	@GetMapping("/index")
	public SseEmitter sse() {
		
		String capture = CaptureScreenUtils.capture();
		SseEmitter emitter = new SseEmitter(100L) ;
		try {
			emitter.send(capture);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return emitter;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
